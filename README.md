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
    implementation "com.github.FlorianMichael:Classic4J:1.0.0"
}
```

## Example usage
### Create instance
To use the API you have to make a new instance of the Classic4J class, you can specify some parameters, if you pass null a default value will be taken
```java
final Classic4J classic4J = new Classic4J(
        null, // UserAgent - used for the web requests
        null // ExternalInterface - For the BetaCraft MP Pass to make the AuthLib request
        );
```

### BetaCraft
Classic4J allows you to dump the server list from betacraft.uk and generate a MP Pass from the BetaCraft launcher, keep in mind that for the MP Pass generator you need to implement the ExternalInterface from above
```java
final BetaCraftHandler betaCraftHandler = classic4J.betaCraftHandler();
        
betaCraftHandler.requestServerList(bcServerList -> {
    System.out.println(bcServerList.servers().size());
    System.out.println(bcServerList.serversOfVersion(BCVersion.ALPHA).size());
    System.out.println(bcServerList.serversWithOnlineMode(false)); // offline mode
});
        
betaCraftHandler.requestMPPass("lyzev", "kevinzockt.de", 25565, s -> {
    // s is the MP Pass
});
```

### ClassiCube
Classic4J allows you to authenticate with ClassiCube and retrieve the server list
```java
final ClassiCubeHandler classiCubeHandler = classic4J.classiCubeHandler();

final CCAccount account = new CCAccount("EnZaXD", "example");
classiCubeHandler.authenticate(account, null, new LoginProcessHandler() {
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
classiCubeHandler.requestServerList(account, ccServerList -> {
    System.out.println(ccServerList.servers().size());
});
```
