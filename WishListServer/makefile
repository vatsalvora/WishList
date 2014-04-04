## Make better later 
## Not tested. Need files that will actually compile
default: WishListServer.class 

WishListServer.class: WishListServer.java DBCom.java
	javac -cp postgresql-9.1-903.jdbc4.jar:.  WishListServer.java DBCom.java

clean:
	rm *.class
