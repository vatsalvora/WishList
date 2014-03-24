## Make better later 
## Not tested. Need files that will actually compile
default: WishListServer.class WLClientTest.class

WishListServer.class: WishListServer.java 
	javac -cp postgresql-9.1-903.jdbc4.jar:. ../WishListFinal/WishList/src/com/wishlist/obj/*.java WishListServer.java DBCom.java

WLClientTest.class: WLClientTest.java 
	javac ../WishListFinal/WishList/src/com/wishlist/obj/*.java ../WishListFinal/WishList/src/com/wishlist/serv/*.java WLClientTest.java 

clean:
	rm *.class
