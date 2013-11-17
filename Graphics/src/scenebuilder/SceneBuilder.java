package scenebuilder;
import geometry.*;
import materials.*;
import lights.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import mathematics.*;

import org.xml.sax.InputSource;

import cameras.OrthograpicCamera;
import cameras.PerspectiveCamera;



/**
  * Class used to build a scene from a given sdl file.
  * Implements the ParserHandler interface (these methods
  * need to be filled in by you).
  * 
  * Note that this class keeps the absolute path to the
  * directory where the sdl file was found.  If you put your
  * textures in the same directory, you can use this path
  * to construct an absolute file name for each texture.
  * You will probably need absolute file names when loading
  * the texture.
  */
public class SceneBuilder implements ParserHandler
{

    // the scene being build
    private Scene scene = null;

    private Scene getScene() { return scene; }

    // the path to the xml directory
    // this path can be used to put in front of the texture file name
    // to load the textures
    private String path = null;

    public String getPath() { return path; }


    /**
     * Load a scene.
     * @param filename The name of the file that contains the scene.
     * @return The scene, or null if something went wrong.
     * @throws FileNotFoundException The file could not be found.
     */
    public Scene loadScene(String filename) throws FileNotFoundException
    {
        // create file and file input stream
        File file = new File(filename);
        FileInputStream fileInputStream = new FileInputStream(file);

        // set the system id so that the dtd can be a relative path
        // the first 2 lines of your sdl file should always be
        //    <?xml version='1.0' encoding='utf-8'?>
        //    <!DOCTYPE Sdl SYSTEM "sdl.dtd">
        // and sdl.dtd should be in the same directory as the dtd
        // if you experience dtd problems, commend the doctype declaration
        //    <!-- <!DOCTYPE Sdl SYSTEM "sdl.dtd"> -->
        // and disable validation (see further)
        // although this is in general not a good idea

        InputSource inputSource = new InputSource(fileInputStream);
        String parentPath = file.getParentFile().getAbsolutePath() + "/";
        path = file.getParentFile().getAbsolutePath() + "/";
        inputSource.setSystemId("file:///" + file.getParentFile().getAbsolutePath() + "/");



        // create the new scene
        scene = new Scene(new SceneGraph());

        // create the parser and parse the input file
        Parser parser = new Parser();
        parser.setHandler(this);

        // if the output bothers you, set echo to false
        // also, if loading a large file (with lots of triangles), set echo to false
        // you should leave validate to true
        // if the docuement is not validated, the parser will not detect syntax errors
        if (parser.parse(inputSource, /* validate */ true, /* echo */ true) == false)
        {
            scene = null;
        }

        // return the scene
        return scene;
    }

    /*
     *  (non-Javadoc)
     * ParserHandler callbacks
     */	

    public void startSdl() throws Exception
    {
    }

    public void endSdl() throws Exception
    {
    }

    public void startCameras() throws Exception
    {
    }

    public void endCameras() throws Exception
    {
    }

    public void startCamera(Point3f position, Vector3f direction, Vector3f up, float fovy, String name) throws Exception
    {
    	Vector4f w = new Vector4f(direction);
		Vector4f u = new Vector4f(up);
		if(name.contains("persp")){
			scene.addCamera(new PerspectiveCamera(position,w,u,fovy,name));
			System.out.println("Persp");
		}
		else if(name.contains("ortho")){
			scene.addCamera(new OrthograpicCamera(position,w,u,fovy,name));
			System.out.println("Ortho");
		}
		else{
			System.out.println("Name should contain ortho(graphic) or persp(ective)");
		} 
    }

    public void endCamera() throws Exception
    {
    }

    public void startLights() throws Exception
    {
    }

    public void endLights() throws Exception
    {
    }

    public void startDirectionalLight(Vector3f direction, float intensity, Color3f color, String name) throws Exception
    {
    	Vector3f normalized = VectorOperations.normalizeVector3f(direction);
    	Vector4f direction4f = new Vector4f(normalized);
    	DirectionalLight dl = new DirectionalLight(direction4f, intensity, color, name);
    	scene.addLight(dl);
    }

    public void endDirectionalLight() throws Exception
    {
    }

    public void startPointLight(Point3f position, float intensity, Color3f color, String name) throws Exception
    {
    	PointLight pl = new PointLight(position, intensity, color, name);
    	scene.addLight(pl);
    }

    public void endPointLight() throws Exception
    {
    }

    public void startSpotLight(Point3f position, Vector3f direction, float angle, float intensity, Color3f color, String name) throws Exception
    {
    	Vector4f direction4f = new Vector4f(direction);
    	SpotLight sl = new SpotLight(position, direction4f, angle, intensity, color, name);
    	scene.addLight(sl);
    }

    public void endSpotLight() throws Exception
    {
    }

    public void startGeometry() throws Exception
    {
    }

    public void endGeometry() throws Exception
    {
    }

    public void startSphere(float radius, String name) throws Exception
    {
    	Sphere sphere = new Sphere(radius,name);
    	scene.addGeometry(sphere);
    }

    public void endSphere() throws Exception
    {
    }

    public void startCylinder(float radius, float height, boolean capped, String name) throws Exception
    {
    	Cylinder cylinder = new Cylinder(radius,height,capped,name);
    	scene.addGeometry(cylinder);
    }

    public void endCylinder() throws Exception
    {
    }

    public void startCone(float radius, float height, boolean capped, String name) throws Exception
    {
    	Cone cone = new Cone(radius,height,capped,name);
    	scene.addGeometry(cone);
    }

    public void endCone() throws Exception
    {
    }

    public void startTorus(float innerRadius, float outerRadius, String name) throws Exception
    {
    	Torus torus = new Torus(innerRadius,outerRadius,name);
    	scene.addGeometry(torus);
    }

    public void endTorus() throws Exception
    {
    }

    public void startTeapot(float size, String name) throws Exception
    {
    }

    public void endTeapot() throws Exception
    {
    }

    public void startIndexedTriangleSet(Point3f [] coordinates, Vector3f [] normals, TexCoord2f [] textureCoordinates, int [] coordinateIndices, int [] normalIndices, int [] textureCoordinateIndices, String name) throws Exception
    { 
    	IndexedTriangleSet its = new IndexedTriangleSet(coordinates,normals,textureCoordinates,coordinateIndices,normalIndices,textureCoordinateIndices,name);
    	scene.addGeometry(its);
    }

    public void endIndexedTriangleSet() throws Exception
    {
    }

    public void startTextures() throws Exception
    {
    }

    public void endTextures() throws Exception
    {
    }

    public void startTexture(String src, String name) throws Exception
    {
    	//TODO
    }

    public void endTexture() throws Exception
    {
    }

    public void startMaterials() throws Exception
    {
    }

    public void endMaterials() throws Exception
    {
    }

    public void startDiffuseMaterial(Color3f color, float ambientFactor, String name) throws Exception
    {
    	DiffuseMaterial dm = new DiffuseMaterial(color, ambientFactor, name); //TODO
    	scene.addMaterial(dm);
    }

    public void endDiffuseMaterial() throws Exception
    {
    }

    public void startPhongMaterial(Color3f color, float shininess, float ambientFactor, String name) throws Exception
    {
    	PhongMaterial pm = new PhongMaterial(color, shininess, ambientFactor, name); //TODO
    	scene.addMaterial(pm);
    }

    public void endPhongMaterial() throws Exception
    {
    }

    public void startLinearCombinedMaterial(String material1Name, float weight1, String material2Name, float weight2, String name) throws Exception
    {
    	HashMap<String,Material> materials = scene.getMaterials();
    	Material material1 = materials.get(material1Name);
    	Material material2 = materials.get(material2Name);
    	if(material1 == null || material2 == null){
    		System.out.println("Materials from which this combined material is made, should be defined first");
    		throw new IllegalArgumentException();
    	}
    	LinearCombinedMaterial lcm = new LinearCombinedMaterial(material1, weight1, material2, weight2, name);
    	scene.addMaterial(lcm);
    }

    public void endLinearCombinedMaterial() throws Exception
    {
    }

    public void startScene(String cameraName, String [] lightNames, Color3f background) throws Exception
    {
    	scene.setCameraToUsed(cameraName);
    	scene.setLightToUsed(lightNames);
    	scene.setBackgroundColor(background);
    }

    public void endScene() throws Exception
    {
    }

    public void startShape(String geometryName, String materialName, String textureName) throws Exception
    {
    	HashMap<String,Geometry> geometrics = scene.getGeometrics();
    	Geometry geometry = geometrics.get(geometryName);
    	HashMap<String,Material> materials = scene.getMaterials();
    	Material material = materials.get(materialName);
    	geometry.setMaterial(material);
    	//TODO : Textures
    	scene.addGeometryToSceneGraph(geometryName);
    }

    public void endShape() throws Exception
    {
    }

    public void startRotate(Vector3f axis, float angle) throws Exception
    {
    	Vector3f normalAxis = VectorOperations.normalizeVector3f(axis);
    	Matrix4f[] matrices = new Matrix4f[2];
    	Matrix4f matrix = MatrixOperations.MakeRotationMatrix(normalAxis,angle);
    	Matrix4f matrix2 = MatrixOperations.MakeRotationMatrix(normalAxis, -angle);
    	matrices[0] = matrix;
    	matrices[1] = matrix2;    	
    	scene.addTransformationMatrices(matrices);
    }

    public void endRotate() throws Exception
    {
    	scene.removeTransformationMatrices();
    }

    public void startTranslate(Vector3f vector) throws Exception
    {
    	Matrix4f[] matrices = new Matrix4f[2];
    	Matrix4f matrix = MatrixOperations.MakeTranslationMatrix(vector);
    	Matrix4f matrix2 = MatrixOperations.MakeTranslationMatrix(VectorOperations.invertVector3f(vector));
    	matrices[0] = matrix;
    	matrices[1] = matrix2;    	
    	scene.addTransformationMatrices(matrices);
    }

    public void endTranslate() throws Exception
    {
    	scene.removeTransformationMatrices();
    }

    public void startScale(Vector3f scale) throws Exception
    {
    	Matrix4f[] matrices = new Matrix4f[2];
    	Matrix4f matrix = MatrixOperations.MakeScalingMatrix(scale);
    	Vector3f inverseScale = new Vector3f();
    	inverseScale.x = (1/scale.x);
    	inverseScale.y = (1/scale.y);
    	inverseScale.z = (1/scale.z);
    	Matrix4f matrix2 = MatrixOperations.MakeScalingMatrix(inverseScale);
    	matrices[0] = matrix;
    	matrices[1] = matrix2;    	
    	scene.addTransformationMatrices(matrices);
    }

    public void endScale() throws Exception
    {
    	scene.removeTransformationMatrices();
    }

}
