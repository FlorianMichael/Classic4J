# Classic4J
Java implementation of the ClassiCube and BetaCraft protocol

## Contact
If you encounter any issues, please report them on the
[issue tracker](https://github.com/FlorianMichael/Classic4J/issues).  
If you just want to talk or need help with Classic4J feel free to join my
[Discord](https://florianmichael.de/discord).

## How to add this to your project
### Gradle/Maven

To use Classic4J with Gradle/Maven you can
use [the Maven Central repository](https://mvnrepository.com/artifact/de.florianmichael/Classic4J)
or [my own repository](https://maven.florianmichael.de/#/releases/de/florianmichael/Classic4J).  
You can also find instructions how to implement it into your build script there.

### Jar File

If you just want the latest jar file you can download it
from [my build server](https://build.florianmichael.de/job/Classic4J), [GitHub Actions](https://github.com/FlorianMichael/Classic4J/actions)
or use the [releases tab](https://github.com/FlorianMichael/Classic4J/releases).

### Requirements
This library requires you to have [Gson](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.10.1) in your class path. <br>
The **minimum** Java version is Java **17**.

### Projects implementing Classic4J
1. [ViaFabricPlus](https://github.com/FlorianMichael/ViaFabricPlus): Fabric mod to connect to EVERY Minecraft server version (Release, Beta, Alpha, Classic, Snapshots, Bedrock) with QoL fixes to the gameplay

## Structure
The two main classes of the library are **BetaCraftHandler** and **ClassiCubeHandler**, where you can get API requests for the respective platforms.

All API Requests are asynchronous and require a callback, the callback is called on the main thread, so you can safely modify the UI in the callback. 
They are located in the **de.florianmichael.classic4j.request** package.

All Models are located in the **de.florianmichael.classic4j.model** and **de.florianmichael.classic4j.api** package.

The internal API is located in the **de.florianmichael.classic4j.util** package. 

**You can either use the high-level frontend for API requests using the Handler classes or use the low-level backend using the Request classes.**

## Example usage
### BetaCraft
Classic4J allows you to dump the server list from https://betacraft.uk/ and generate an MP Pass from the BetaCraft launcher, keep in mind that for the MP Pass generator you need to implement the ExternalInterface from above
```java
BetaCraftHandler.requestServerList(serverList -> {
    System.out.println(serverList.servers().size());
    System.out.println(serverList.serversOfVersion(BCVersion.ALPHA).size());
    System.out.println(serverList.serversWithOnlineMode(false)); // offline mode
});

// You can authenticate to a BetaCraft server by doing:
BetaCrafthandler.authenticate(serverId -> {
    // You have to call the joinServer Statement in here     
});
```

### ClassiCube
Classic4J allows you to authenticate with ClassiCube and retrieve the server list
```java
final CCAccount account = new CCAccount("<username>", "<passowrd>");
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
ClassiCubeHandler.requestServerList(account, serverList -> {
    System.out.println(serverList.servers().size());
});
```
