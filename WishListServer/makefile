## Make better later
default: WishListServer.class WLClientTest.class

WishListServer.class: WishListServer.java FBUser.java \
    WishItem.java StringPair.java
	javac WishListServer.java FBUser.java WishItem.java StringPair.java

WLClientTest.class: WLClientTest.java FBUser.java WishItem.java WLServerCom.java StringPair.java
	javac WLClientTest.java FBUser.java WishItem.java WLServerCom.java StringPair.java

clean:
	rm *.class
