public class NBody{
  /*N represents # of planets (N rows each with 6 values)*/
  /*R represents radius of universe*/

  public static void main (String[] args) {
    double T = Double.parseDouble(args[0]);
    double dt = Double.parseDouble(args[1]);
    String filename = args[2];
    double radius = readRadius(filename);
    Body[] bodies = readBodies(filename);

    StdDraw.setScale(-radius, radius);
    StdDraw.picture(0,0,"images/starfield.jpg");
    StdDraw.show();

    for (int i = 0; i < bodies.length; i++){
      bodies[i].draw();
    }
    StdDraw.enableDoubleBuffering();
    int time = 0;
    int num_bodies = bodies.length;

    while(time < T){
      double[] xForces = new double[num_bodies];
      double[] yForces = new double[num_bodies];
      for (int i = 0; i < num_bodies; i++){
        /*For every single planet, find the net force total due to all the other planets*/
        double xres = bodies[i].calcNetForceExertedByX(bodies);
        double yres = bodies[i].calcNetForceExertedByY(bodies);
        xForces[i] = xres;
        yForces[i] = yres;
      }
      for (int i = 0; i < num_bodies; i++){
        bodies[i].update(dt, xForces[i], yForces[i]);
      }

      StdDraw.picture(0,0,"images/starfield.jpg");

      for (int i = 0; i < bodies.length; i++){
        bodies[i].draw();
      }
      StdDraw.show();
      StdDraw.pause(10);
      time += dt;

    }

    StdOut.printf("%d\n", bodies.length);
    StdOut.printf("%.2e\n", radius);
    for (int i = 0; i < bodies.length; i++) {
        StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                  bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
            }

  }

  public static double readRadius (String file){
    /*Returns the radius of the universe corresponding to this file*/
    In in = new In(file);
    int bodies = in.readInt();
    double radius = in.readDouble();
    return radius;
  }

  public static Body[] readBodies (String file){

    In in = new In(file);
    int bodies = in.readInt();
    double radius = in.readDouble();
    Body[] body_list = new Body[bodies];
    for (int i = 0; i < bodies; i++ ){
      double x = in.readDouble();
      double y = in.readDouble();
      double xvel = in.readDouble();
      double yvel = in.readDouble();
      double mass = in.readDouble();
      String img = in.readString();
      body_list[i] = new Body(x,y,xvel,yvel,mass,img);
    }
    return body_list;
  }
}
