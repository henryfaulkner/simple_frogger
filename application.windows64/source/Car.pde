class Car extends Rectangle { //"car is a rectangle"
  float speed;
  PImage car_img;
  
  Car(PImage img, float x, float y, float w, float h, float s){
    super(x, y, w, h);
    speed = s;
    car_img = img;
  }
  
  void show() {
    fill(200);
    image(car_img, x, y, w, h);
  }
  
  void update() {
    x = x + speed;
  
    if(x > width+grid && speed > 0)
      x = -w-grid;  
    else if(x < -w-grid && speed < 0)
      x = width+grid;
  }
  
}
