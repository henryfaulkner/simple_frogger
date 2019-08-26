import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Shitty_Frogger extends PApplet {

Frog frog; 
Car[] cars;
Log[] logs;
float grid = 50;
PImage frog_img;
PImage right_img;
PImage left_img;
PImage truck_img;
PImage log_img;

public void resetGame(){
  frog = new Frog(frog_img, width/2-grid/2, height-grid, grid);
  println("GAME OVER :( !");
}

public void setup() {
  
  cars = new Car[8];
  frog_img = loadImage("baby_frog.png");
  frog = new Frog(frog_img, width/2-grid/2, height-grid, grid);
  
  int index = 0;
  // ROW 2
  for(int i = 0; i < 2; i++){
    float x = i * 300;
    truck_img = loadImage("truck.png");
    cars[index] = new Car(truck_img, x, height-grid*2, grid*2, grid, 2);
    index++;
  }
  
  // ROW 3
  for(int i = 0; i < 2; i++) {
    float x = i * 200 + 150;
    left_img = loadImage("car_left.png");
    cars[index] = new Car(left_img, x, height-grid*3, grid, grid, -3.5f);
    index++;
  }
  
  // ROW 4
  for(int i = 0; i < 4; i++) {
    float x = i * 200 + 150;
    right_img = loadImage("car_right.png");
    cars[index] = new Car(right_img, x, height-grid*4, grid, grid, 1.5f);
    index++;
  }
  
  logs = new Log[7];
  log_img = loadImage("log.png");
  // ROW 6
  index = 0;
  for(int i = 0; i < 2; i++) {
    float x = i * 350 + 25;
    logs[index] = new Log(log_img, x, height-grid*6, grid*3, grid, 2);
    index++;
  }
  
  // ROW 7
  for(int i = 0; i < 3; i++) {
    float x = i * 200 + 25;
    logs[index] = new Log(log_img, x, height-grid*7, grid*2, grid, -1.3f);
    index++;
  }
  
  // ROW 8
  for(int i = 0; i < 2; i++) {
    float x = i * 400 + 100;
    logs[index] = new Log(log_img, x, height-grid*8, grid*3, grid, .5f);
    index++;
  }
}

public void draw() {
  background(0);
  fill(255, 100);
  rect(0, height-grid, width, grid);
  rect(0, height-grid*5, width, grid);
  rect(0, 0, width, grid);
  
  for(Car car : cars){
    car.show();
    car.update();
    if(frog.intersects(car)){
       resetGame();
    }
  }
  
  for(Log log : logs){
    log.show();
    log.update();
  }
  
  if(frog.y < height-grid*5 && frog.y > 0){
    boolean ok = false; //not ok
    for(Log log : logs){
      if(frog.intersects(log)){
        ok = true;
        frog.attach(log);
      }
    }
    if(!ok)
      resetGame();
  } else {
    frog.attach(null); 
  }

  frog.update();
  frog.show();
}

public void keyPressed() {
   switch(keyCode) {
     case UP:
       frog.move(0, -1); break;
     case DOWN:
       frog.move(0, 1); break;
     case RIGHT:
       frog.move(1, 0); break;
     case LEFT:
       frog.move(-1, 0); break;
   }
}
class Car extends Rectangle { //"car is a rectangle"
  float speed;
  PImage car_img;
  
  Car(PImage img, float x, float y, float w, float h, float s){
    super(x, y, w, h);
    speed = s;
    car_img = img;
  }
  
  public void show() {
    fill(200);
    image(car_img, x, y, w, h);
  }
  
  public void update() {
    x = x + speed;
  
    if(x > width+grid && speed > 0)
      x = -w-grid;  
    else if(x < -w-grid && speed < 0)
      x = width+grid;
  }
  
}
class Frog extends Rectangle { //inheritance; "frog is a rectangle"
  
  Log attached = null;
  PImage frog_img;
  
  Frog(PImage img, float x, float y, float w) {
    super(x, y, w, w);//frog is square
    frog_img = img;
  }
  
  public void attach(Log log){
    attached = log; 
  }
  
  public void update(){
     if(attached!=null){
        frog.x += attached.speed; 
     }
     
     frog.x = constrain(x, 0, width-w);
  }
  
  public void show() {
    fill(255);
    image(frog_img, x, y, w, w);
  }
  
  public void move(float xdir, float ydir) {
    x += xdir * grid;
    y += ydir * grid;
  }
  
}
class Log extends Car { //"log is a car"
  
  Log(PImage img, float x, float y, float w, float h, float s){
    super(img, x, y, w, h, s);
  }
  
}
class Rectangle {
 float x;
 float y;
 float w;
 float h;
 
 Rectangle(float x, float y, float w, float h){
    this.x = x;
    this.w = w;
    this.y = y;
    this.h = h;
 }
 
 public boolean intersects(Rectangle other){
   float left = x;
   float right = x + w;
   float top = y;
   float bottom = y + h;
    
   float oleft = other.x;
   float oright = other.x + other.w;
   float otop = other.y;
   float obottom = other.y + other.h;
   
   return !(left >= oright || right <= oleft ||
   top >= obottom || bottom <= otop); 
 }
}
  public void settings() {  size(500, 450); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Shitty_Frogger" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
