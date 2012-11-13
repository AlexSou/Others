import java.util.ArrayList;
import java.util.Arrays;


public class MySubmission implements Submission {

	protected double[] sx;
	protected double[] sy;
	protected double[] sz;
	protected double[] sr;

	public MySubmission(){

	}

	public void sendSpheres (Sphere[] spheres) { 
		
		
		
		sx = new double[spheres.length];
		sy = new double[spheres.length];
		sz = new double[spheres.length];
		sr = new double[spheres.length];
		
		
		for(int i = 0; i< spheres.length; i++){
			Vector3 v = spheres[i].pos;
			sx[i] = v.x;
			sy[i] = v.y;
			sz[i] = v.z;
			sr[i] = spheres[i].radius;
			
		}
		
		}
	public int[] processFrame(final double x,
			final double y,
			final double z) throws Exception {
		if (sx==null) sendSpheres(TestFramework.getSpheres());
		final ArrayList<Integer> result0 = new ArrayList<Integer>();
		final ArrayList<Integer> result1 = new ArrayList<Integer>();

		Thread t = new Thread(new Runnable() { public void run() {
			for (int i=0 ; i<sx.length/2 ; ++i) {


				if(intersects(i, x, y, z))
					result0.add(i);

			}
		}});
		t.start();
		for (int i=sx.length/2; i<sx.length ; ++i) {

			if(intersects(i, x, y, z))
				result1.add(i);
		}
		t.join();

		int size = result0.size() + result1.size();
		int i_offset = result0.size();
		int[] result2 = new int[size];
		for (int i=0 ; i<result0.size() ; ++i) {
			result2[i] = result0.get(i);
		}
		for (int i=0 ; i<result1.size() ; ++i) {

			result2[i_offset] = result1.get(i);
			i_offset++;
		}

		Arrays.sort(result2);

		return result2;

	}

	
	private final boolean intersects(int i, double vx, double vy, double vz){

		double r2 = sr[i] * sr[i];
		
		double newX = vx - sx[i];
		
		
		double length = newX * newX;

		if(length >= r2)
			return false;
		
		double newY = vy - sy[i];
		
		length += newY * newY;

		if(length >= r2)
			return false;
		
		double newZ = vz - sz[i];
		length += newZ * newZ;

		return length < r2;

	}
	
}


