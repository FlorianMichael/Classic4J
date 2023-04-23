# Classic4J
Java implementation of the ClassiCube and BetaCraft protocol

This project is based on [ViaFabricPlus PR No. 31](https://github.com/FlorianMichael/ViaFabricPlus/pull/31) and [BetacraftServerListParser](https://github.com/allinkdev/BetacraftServerListParser) <br>
Huge thanks to [Allink](https://github.com/allinkdev) for contributing!

## Contact
If you encounter any issues, please report them on the
[issue tracker](https://github.com/FlorianMichael/Classic4J/issues).  
If you just want to talk or need help with Classic4J feel free to join my
[Discord](https://discord.gg/BwWhCHUKDf).

## How to add this to your project
Just copy this part to your *build.gradle*:
```groovy
repositories {
    maven {
        name = "Jitpack"
        url = "https://jitpack.io"
    }
}

dependencies {
    implementation "com.github.FlorianMichael:Classic4J:1.2.0"
}
```
This library requires you to have [Gson](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.10.1) in your class path

## Structure
There is no main class, there are three classes in the package: **BetaCraftHandler**, **ClassiCubeHandler** and **JSPBetaCraftHandler**, in these classes are the API requests for the respective platforms.

## Example usage
### BetaCraft
Classic4J allows you to dump the server list from betacraft.uk and generate a MP Pass from the BetaCraft launcher, keep in mind that for the MP Pass generator you need to implement the ExternalInterface from above
```java
BetaCraftHandler.requestServerList(bcServerList -> {
    System.out.println(bcServerList.servers().size());
    System.out.println(bcServerList.serversOfVersion(BCVersion.ALPHA).size());
    System.out.println(bcServerList.serversWithOnlineMode(false)); // offline mode
});

final String mpPass = JSPBetaCraftHandler.requestMPPass("lyzev", "kevinzockt.de", 25565, serverId -> {
    // You have to call the joinServer Statement in here     
});
```

### ClassiCube
Classic4J allows you to authenticate with ClassiCube and retrieve the server list
```java
final CCAccount account = new CCAccount("EnZaXD", "example");
ClassiCubeHandler.requestAuthentication(account, null, new LoginProcessHandler() {
    @Override
    public void handleMfa(CCAccount account) {
        // Called when the account requires to be verified via MFA
        // If this is the case, you can call the authenticate method again and specify the MFA code instead of null
    }

    @Override
    public void handleSuccessfulLogin(CCAccount account) {
        // Called when the login was successfully
    }

    @Override
    public void handleException(Throwable throwable) {
        // Called when something went wrong
    }
});
```
Once you are authenticated, you can then dump the server list like BetaCraft, other API requests like searching are also implemented
```java
ClassiCubeHandler.requestServerList(account, ccServerList -> {
    System.out.println(ccServerList.servers().size());
});
```
