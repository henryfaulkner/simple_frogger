Frog frog; 
Car[] cars;
Log[] logs;
float grid = 50;
PImage frog_img;
PImage right_img;
PImage left_img;
PImage truck_img;
PImage log_img;

void resetGame(){
  frog = new Frog(frog_img, width/2-grid/2, height-grid, grid);
  println("GAME OVER :( !");
}

void setup() {
  size(500, 450);
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
    cars[index] = new Car(left_img, x, height-grid*3, grid, grid, -3.5);
    index++;
  }
  
  // ROW 4
  for(int i = 0; i < 4; i++) {
    float x = i * 200 + 150;
    right_img = loadImage("car_right.png");
    cars[index] = new Car(right_img, x, height-grid*4, grid, grid, 1.5);
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
    logs[index] = new Log(log_img, x, height-grid*7, grid*2, grid, -1.3);
    index++;
  }
  
  // ROW 8
  for(int i = 0; i < 2; i++) {
    float x = i * 400 + 100;
    logs[index] = new Log(log_img, x, height-grid*8, grid*3, grid, .5);
    index++;
  }
}

void draw() {
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

void keyPressed() {
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
