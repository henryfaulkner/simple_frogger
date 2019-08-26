class Frog extends Rectangle { //inheritance; "frog is a rectangle"
  
  Log attached = null;
  PImage frog_img;
  
  Frog(PImage img, float x, float y, float w) {
    super(x, y, w, w);//frog is square
    frog_img = img;
  }
  
  void attach(Log log){
    attached = log; 
  }
  
  void update(){
     if(attached!=null){
        frog.x += attached.speed; 
     }
     
     frog.x = constrain(x, 0, width-w);
  }
  
  void show() {
    fill(255);
    image(frog_img, x, y, w, w);
  }
  
  void move(float xdir, float ydir) {
    x += xdir * grid;
    y += ydir * grid;
  }
  
}
