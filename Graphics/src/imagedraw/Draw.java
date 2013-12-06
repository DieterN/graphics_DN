package imagedraw;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import scenebuilder.*;
import mathematics.*;

/**
 * Basic class for drawing, all it's paramters are contained in the DrawController class.
 * But you need to decide here which DrawController to use in the Draw() constructor !!!
 *  
 * @author Dieter
 *
 */
public class Draw implements MouseListener{

	public static void main(String[] args) {
		new Draw();
	}

	private DrawController ic;
    // bepaal of je object of Ray transformeert door het type IC Controller (set in Draw() constructor)
	// je kan ook kiezen om met een CompactGrid of Bounding Interval Hierarchy te werken
	private JFrame frame;
	private CgPanel panel;
	private Scene scene;

	public Draw() {
		try {
			SceneBuilder sceneBuilder = new SceneBuilder();
			scene = sceneBuilder.loadScene("XML/basis.sdl");
			ic = new DCCompactGrid(scene); //DECIDE WHICH CONTROLLER TO USE!!!!!
//			ic = new DCBoundingIntervalHierarchy(scene); //DECIDE WHICH CONTROLLER TO USE!!!!!
//			ic = new DCTransformObject(scene); //DECIDE WHICH CONTROLLER TO USE!!!!!
//			ic = new DCTransformRay(scene); //DECIDE WHICH CONTROLLER TO USE!!!!!
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		panel = new CgPanel();
		panel.addMouseListener(this);
		frame = new JFrame();
		frame.setSize(DrawController.getNx(),DrawController.getNy());
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

	public void mousePressed(MouseEvent e) { }
	public void mouseClicked(MouseEvent e) { drawSceneAndTime(); }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }

	public void drawSceneAndTime() {
		long begintime = System.currentTimeMillis();
		Color3f bgC = scene.getBackgroundColor();
		panel.clear(bgC.x,bgC.y,bgC.z);
		if(!(scene == null)){
			drawScene();
		}
		else{
			System.out.println("Scene is null!");
		}
		panel.repaint();
		panel.flush();
		long endtime = System.currentTimeMillis();
		System.out.println("Time: " + (endtime - begintime) + " ms");
		panel.saveImage("image.png");
	}
	
	public void drawScene(){
		for(int i=0; i<DrawController.getNx(); i++){
			if(i%20 == 0){
				double percentage = (double) i/DrawController.getNx()*100;
				System.out.println("Rendering... " + percentage + " %");
			}
			for(int j=0; j<DrawController.getNy(); j++){
				Color3f color = ic.calculatePixelColor(i, j);
				if(!DrawController.falseColorImage){
					panel.drawPixel(i,DrawController.getNy()-j,color.x,color.y,color.z); //ny-j, want y-as java loopt naar beneden 
				}
				DrawController.setCurrentPixel(i+DrawController.getNx()*j); //needed for false color image
			}
		}
		if(DrawController.falseColorImage){
			drawFalseColorImage();
		}
	}
	
	public void drawFalseColorImage(){
		System.out.println("Generating false color image");
		int[] pixels = DrawController.getIntersectionsPerPixel();
		float maxIntersections = 0;
		for(int i : pixels){ //get max number intersections
			if(i>maxIntersections){
				maxIntersections = i;
			}
		}
		float[] normalizedPixels = new float[pixels.length];
		for(int j=0; j<pixels.length; j++){ //normalize all pixels, so greatest is 1
			normalizedPixels[j] = pixels[j]/maxIntersections;
		}
		for(int i=0; i<DrawController.getNx(); i++){
			for(int j=0; j<DrawController.getNy(); j++){
				Color3f color = new Color3f();
				color.x = normalizedPixels[i+DrawController.getNx()*j];
				if(color.x > 1){
					color.x = 1;
				}
				panel.drawPixel(i,DrawController.getNy()-j,color.x,color.y,color.z); //ny-j, want y-as java loopt naar beneden
			}
			
		}
	}
	
}
