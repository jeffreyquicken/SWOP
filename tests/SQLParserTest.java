

import static org.junit.jupiter.api.Assertions.*;

import SQLQuery.Query;
import org.junit.jupiter.api.Test;

import static SQLQuery.SQLParser.parseQuery;

class SQLParserTest {

	static void assertRoundTrip(String query) {
		assertEquals(query, parseQuery(query));
	}
	
	@Test
	void test() {
		//assertRoundTrip("SELECT movie.title AS title FROM movies AS movie WHERE movie.imdb_score > 7");
		Query query = parseQuery("SELECT movie.title AS title FROM movies AS movie WHERE TRUE");
		System.out.println(query);

	//	assertRoundTrip("SELECT student.name AS name, student.program AS program" +
		//		" FROM enrollments AS enrollment INNER JOIN students AS student" +
		//		" ON enrollment.student_id = student.student_id" +
		//		" WHERE enrollment.course_id = \"SWOP\"");
		//assertRoundTrip("SELECT parent.name AS parentName, child.name AS childName" +
		//		" FROM persons AS parent" +
		//		" INNER JOIN is_child_of AS link ON parent.id = link.parent_id" +
		//		" INNER JOIN persons AS child ON link.child_id = child.id" +
		//		" WHERE TRUE");
	}

}
