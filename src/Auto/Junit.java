package Auto;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class Junit {

	private boolean result;
	private String casename;

	public Junit(String casename, boolean result) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		this.casename = casename;
		this.result = result;
	}

	// @Parameters(name = "{index}. CaseName:{0}")
	@Parameters(name = "{0}")
	public static Collection primeNumbers()
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException, ClassNotFoundException, IOException {
		method Method = new method();
		ArrayList<ArrayList> ResultList = Method.method();
		Object obj[][] = new Object[ResultList.size()][ResultList.get(0).size()];
		for (int i = 0; i < ResultList.size(); i++) {
			for (int j = 0; j < ResultList.get(i).size(); j++) {
				obj[i][j] = ResultList.get(i).get(j);
			}
		}
		List list = Arrays.asList(obj);
		
		return list;
		// Arrays.asList(new
		// Object[][]{{"login",true},{"logout",false}});
	}

	@Test
	public void Case() {
		assertTrue(result);
	}

}
