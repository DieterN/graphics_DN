package imagedraw;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import rays.*;
import scenebuilder.*;
import mathematics.*;


public class Draw implements MouseListener{

	public static void main(String[] args) {
		new Draw();
	}

	private IntersectController ic = new ICTransformObject(); 
//  bepaal of je object of Ray transformeert door het type IC Controller
//	private int x;
//	private int y;
	private JFrame frame;
	private CgPanel panel;
	private Scene scene;
	private static final int nx = 640; //number of pixels, x-direction
	private static final int ny = 480; //number of pixels, y-direction

	public Draw() {
		try {
			SceneBuilder sceneBuilder = new SceneBuilder();
			scene = sceneBuilder.loadScene("XML/test2.sdl");
			ic.setScene(scene);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		panel = new CgPanel();
		panel.addMouseListener(this);
		frame = new JFrame();
		frame.setSize(nx,ny);
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
		for(int i=0; i<nx; i++){
			for(int j=0; j<ny; j++){
				HitRecord hr = ic.lookForRayHit(i,j);
				if(hr != null){	
					Color3f color = new Color3f();
					//AMBIENT
					color.x = hr.getColor().x*hr.getAmbientFactor();
					color.y = hr.getColor().y*hr.getAmbientFactor();
					color.z = hr.getColor().z*hr.getAmbientFactor();
					// TODO : reflective + refraction
					if(!ic.inShadow(hr)){
						Color3f shadingColor = ic.calculateShading(hr);
						color.x += shadingColor.x;
						color.y += shadingColor.y;
						color.z += shadingColor.z;
					}
					Color3f rightColor = Color3f.checkColorsGreaterThanOne(color);
					panel.drawPixel(i,ny-j,rightColor.x,rightColor.y,rightColor.z); //ny-j, want y-as java loopt naar beneden 
				} // geen else, achtergrond getekend in drawSceneAndTime
			}
		}
	}
	
	public static int getNx() {
		return nx;
	}

	public static int getNy() {
		return ny;
	}
}
