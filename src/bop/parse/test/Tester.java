package bop.parse.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

public class Tester {
	
	

	//test to see if the read went fine and if the file was created.
	@Test
	public void testReadWasFine(){
		try{
			TestClass.getDataFromURL();
			File f = new File("temp.pdf");
			assertTrue(f.exists());
		}catch(Exception ex){
			fail();
		}
	}
	
	//Test that the parser is returning data back
	@Test
	public void testStringFromSort(){
		try{
			assertTrue(!TestClass.parseData().isEmpty());
		}catch(Exception e){
			fail();
		}
	}
	
	
	//Depreciated test - delete is done inside of parser for this file.
	//Test that the delete function works
	/*@Test
	public void testDeleteFile(){
		try{
			File f = new File("temp.data");
			if(f.exists()){
				TestClass.deleteTheFile(f);
				assertTrue(!f.exists());
			}else{
				fail();
			}
		}catch(Exception ex){
			fail();
		}
	}*/
}
