package model;

public enum Gender {
 Man("男性"),
 Woman("女性"), 
 Other("その他");
	
private final String name;	
	
 private Gender(String name) {
	this.name = name;
}
 public String getName() {
	 return this.name;
 }
 
}
