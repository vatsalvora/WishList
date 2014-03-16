## Make better later
default: WishListServer.class WLClientTest.class

WishListServer.class: WishListServer.java FBUser.java \
    WishItem.java StringPair.java DBCom.java
	javac -cp postgresql-9.1-903.jdbc4.jar:. WishListServer.java FBUser.java WishItem.java StringPair.java DBCom.java

WLClientTest.class: WLClientTest.java FBUser.java WishItem.java WLServerCom.java StringPair.java
	javac WLClientTest.java FBUser.java WishItem.java WLServerCom.java StringPair.java

clean:
	rm *.class
