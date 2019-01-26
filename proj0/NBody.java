
public class NBody{
  /*N represents # of planets (N rows each with 6 values)*/
  /*R represents radius of universe*/
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
