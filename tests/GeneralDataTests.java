import Data.*;
import UserInterfaceElements.Controller;
import UserInterfaceElements.MyCanvasWindow;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GeneralDataTests {
	//CellBoolean tests
	@Test
	public void testBool() {
		CellBoolean cell = new CellBoolean(true);
		
		assertEquals("Boolean", cell.getType());
		assertEquals(true, cell.getValue());
		assertEquals("True", cell.getString());
		cell.setValue(false);
		assertEquals(false, cell.getValue());
		assertEquals("False", cell.getString());	
	}
	
	//CellEmail tests
	
	@Test
	public void testEmail() {
		CellEmail cell = new CellEmail("test.email@email.com");
		
		assertEquals(true, cell.isValidEmailAddress("test.email@email.com"));
		assertEquals("Email", cell.getType());
		assertEquals("test.email@email.com", cell.getValue());
		assertEquals("test.email@email.com", cell.getString());
		assertEquals(false, cell.isValidEmailAddress("test"));
		cell.setValue("test");
		assertEquals("test.email@email.com", cell.getValue());
		cell.delChar();
		assertEquals("test.email@email.co", cell.getValue());
		cell.addChar('p');
		assertEquals("test.email@email.cop", cell.getValue());
	}
	//CellInteger tests
	@Test
	public void testInteger() {
		CellInteger cell = new CellInteger(5);
		assertEquals("Integer", cell.getType());
		assertEquals(5, cell.getValue().intValue());
		assertEquals("5", cell.getString());
		cell.setValue(10);
		assertEquals(10, cell.getValue().intValue());
		cell.delChar();
		assertEquals(1, cell.getValue().intValue());
		cell.addChar('5');
		assertEquals(15, cell.getValue().intValue());
		try{
			cell.addChar('p');
			fail("");
		}
		catch(Exception e) {
			assertTrue(e instanceof NumberFormatException);
		}
	}
	
	//CellText tests
	@Test
	public void testText() {
		CellText cell = new CellText("test");
		
		assertEquals("Text", cell.getType());
		assertEquals("test", cell.getValue());
		assertEquals("test", cell.getString());
		cell.setValue("testing");
		assertEquals("testing", cell.getValue());
		cell.delChar();
		assertEquals("testin", cell.getValue());
		cell.addChar('p');
		assertEquals("testinp", cell.getValue());
	}
}
