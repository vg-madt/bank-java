package bank;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractManager {
	protected static final ObjectMapper mapper = new ObjectMapper();
	public <T> T read(String location, Class<T> type) {
		try {
			File file = new File(location);
			final T value = mapper.readValue (file,type);
			return value;
			}catch(JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return null;
	}
	public void write(String location,Object object) {
		try {
		File file = new File(location);
//		if(file.exists()) {
//			
//		}
		mapper.writeValue(file,object);
		}catch(JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
