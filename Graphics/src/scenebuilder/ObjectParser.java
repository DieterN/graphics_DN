package scenebuilder;

import java.io.*;
import java.util.*;

import mathematics.*;

public class ObjectParser {

	private final static String vertex = "v ";
	private final static String normal = "vn";
	private final static String texture = "vt";
	private final static String face = "f";
	private final static String path = "Objects/";
	
	private List<Point3f> coordinates = new ArrayList<Point3f>();
	private List<Vector3f> normals = new ArrayList<Vector3f>();
	private List<TexCoord2f> textureCoordinates = new ArrayList<TexCoord2f>();
	private List<Integer> coordinateIndices = new ArrayList<Integer>();
	private List<Integer> normalIndices = new ArrayList<Integer>();
	private List<Integer> textureCoordinateIndices = new ArrayList<Integer>();
	
	public ObjectParser(){
		
	}
	
	public void parseObjectFile(String filename) throws IOException, ParseException{
		String pathname = path + filename;
		File file = new File(pathname);
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		while(br.ready()){ //zolang er iets te lezen valt
			String line = br.readLine();
			
				if(line.startsWith(vertex)){
					calculateVertex(line);
				}
				else if(line.startsWith(normal)){
					calculateNormal(line);
				}
				else if(line.startsWith(texture)){
					calculateTexture(line);
				}
				else if(line.startsWith(face)){
					calculateFace(line);
				}
			}
		br.close();
		}

	private void calculateVertex(String line) throws ParseException {
		String [] split = line.split(" ", 2);
		Point3f coordinate = ParserUtils.parsePoint3f(split[1]);
		coordinates.add(coordinate);
	}

	private void calculateNormal(String line) throws ParseException {
		String [] split = line.split(" ", 2);
		Vector3f normal = ParserUtils.parseVector3f(split[1]);
		normals.add(normal);
	}
	
	private void calculateTexture(String line) throws ParseException {
		String [] split = line.split(" ", 2);
		TexCoord2f tex = ParserUtils.parseTexCoord2f(split[1]);
		textureCoordinates.add(tex);		
	}
	
	private void calculateFace(String line) {
		String linex = line.substring(face.length());
		StringTokenizer st1 = new StringTokenizer(linex," ");
		if(linex.contains("//")){
			//v1//vn1 v2//vn2 v3//vn3 
			for(int i=1; i<=3; i++){
				String indices = st1.nextToken();
				StringTokenizer st2 = new StringTokenizer(indices,"//");
				coordinateIndices.add(Integer.parseInt(st2.nextToken())-1);
				normalIndices.add(Integer.parseInt(st2.nextToken())-1);
			}
		}
		else{
			//v1/vt1/vn1 v2/vt2/vn2 v3/vt3/vn3 
			for(int i=1; i<=3; i++){
				String indices = st1.nextToken();
				StringTokenizer st2 = new StringTokenizer(indices,"/");
				coordinateIndices.add(Integer.parseInt(st2.nextToken())-1);
				textureCoordinateIndices.add(Integer.parseInt(st2.nextToken())-1);
				normalIndices.add(Integer.parseInt(st2.nextToken())-1);
			}
		}
	}

	public List<Point3f> getCoordinates() {
		return coordinates;
	}

	public List<Vector3f> getNormals() {
		return normals;
	}

	public List<TexCoord2f> getTextureCoordinates() {
		return textureCoordinates;
	}

	public List<Integer> getCoordinateIndices() {
		return coordinateIndices;
	}

	public List<Integer> getNormalIndices() {
		return normalIndices;
	}

	public List<Integer> getTextureCoordinateIndices() {
		return textureCoordinateIndices;
	}	
}
