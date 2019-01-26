import java.lang.Math;

public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G = 6.67e-11;

    public Body(double xP, double yP, double xV, double yV, double m, String img){
      /*Constructs new Body*/
      xxPos = xP;
      yyPos = yP;
      xxVel = xV;
      yyVel = yV;
      mass = m;
      imgFileName = img;
    }

    public Body(Body b){
      /*Makes a copy of a Body*/
       xxPos = b.xxPos;
       yyPos = b.yyPos;
       xxVel = b.xxVel;
       yyVel = b.yyVel;
       mass = b.mass;
       imgFileName = b.imgFileName;
    }

    public void draw(){
        StdDraw.picture(xxPos,yyPos,"images/"+ imgFileName);
        StdDraw.show();
    }
    public double calcDistance(Body b){
      /*Finds distance between body b and the body currently performing calculation*/
      double dx = this.xxPos-b.xxPos;
      double dy = this.yyPos-b.yyPos;
      return Math.sqrt((dx*dx) + (dy*dy));
    }
    public double calcForceExertedBy(Body b){
      /*Calculates force exerted by b on this body*/
      double dist = calcDistance(b);
      return (G*(b.mass)*(this.mass))/(dist*dist);
    }

    public double calcForceExertedByX(Body b){
      /*Calculates x component of force*/
      /*Find dx and dy by subtracting b's values on this body*/
      double dx = b.xxPos - this.xxPos;
      return (calcForceExertedBy(b) * dx) / calcDistance(b);
    }

    public double calcForceExertedByY(Body b){
      /*Calculates y component of force*/
      double dy = b.yyPos - this.yyPos;
      return (calcForceExertedBy(b) * dy) / calcDistance(b);
    }

    public double calcNetForceExertedByX(Body[] b_array){
      /*Calculates the net x component of force on this body by b*/
      /*Make sure to take into account the fact that it cannot exert force on itself*/
      double netforce = 0;
      for (int x = 0; x < b_array.length; x++){
          if (!(this.equals(b_array[x]))){
            netforce += calcForceExertedByX(b_array[x]);
          }
      }
      return netforce;
    }
    public double calcNetForceExertedByY(Body[] b_array){
      /*Calculates the net x component of force on this body by b*/
      /*Make sure to take into account the fact that it cannot exert force on itself*/
      double netforce = 0;
      for (int x = 0; x < b_array.length; x++){
          if (!(this.equals(b_array[x]))){
            netforce += calcForceExertedByY(b_array[x]);
          }
      }
      return netforce;
    }

    public void update(double dt, double xforce, double yforce){
      /*Updates current body's velocity and position if x-force and y-force is applied to it for dt seconds*/
      double ax = xforce/this.mass;
      double ay = yforce/this.mass;
      this.xxVel = this.xxVel + dt*ax;
      this.yyVel = this.yyVel + dt*ay;
      this.xxPos = this.xxPos + dt*this.xxVel;
      this.yyPos = this.yyPos + dt*this.yyVel;
    }
}
