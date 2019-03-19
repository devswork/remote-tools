package com.github.devswork.util;

import java.io.*;
import java.security.MessageDigest;
import java.util.Properties;

/**
 * @author devswork
 */
public class FileUtil {

    static final char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7','8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    static final String HASH_TYPE = "SHA1";
    public static final int FILE_BYTE_SIZE = 1024;
    public static final int FILE_CACHE_SIZE = 2048;
    public static final int FILE_CONTENT_SIZE = 1000;
    public static final String sep = File.separator;
    public static final String defaultEncoding = "utf-8";
    public static String getExtName(File f){
    	String fileName = f.getName();
    	String ext = "";
    	if(fileName.lastIndexOf(".")>=0){
    		ext = fileName.substring(fileName.lastIndexOf(".")+1);
    	}
    	return ext;
    }

    public static int getFileSize(String fullPath) {
        return getFileSize(new File(fullPath));
    }

    public static int getFileSize(File f) {
        int attSize = 0;
        try {
            attSize = (int)f.length() / FILE_BYTE_SIZE;
            if(f.length() > 0 && f.length() < 1) {
                attSize = 1;
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return attSize;
    }

    public static void copyFile(String fromFullPath, String toFullPath) throws Exception {
    	copyFile(new File(fromFullPath),new File(toFullPath));
    }

    public static void copyFile(File fromFile, File toFile) throws Exception {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            File toPath = new File(toFile.getParent());
            if(toPath.exists() == false) {
                toPath.mkdirs();
            }
            bis = new BufferedInputStream(new FileInputStream(fromFile));
            bos = new BufferedOutputStream(new FileOutputStream(toFile));
            byte[] buff = new byte[FILE_CACHE_SIZE];
            int bytesRead;
            while(-1 != (bytesRead = (bis.read(buff, 0, buff.length)))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch(FileNotFoundException fe) {
            System.out.println(fe.getMessage());
            throw new FileNotFoundException(fe.getMessage());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        } finally {
            bis.close();
            bos.close();
        }
    }

    public boolean exists(String path, String fileName) {
        boolean b = false;
        File dir = new File(path);
        if(dir.isDirectory()) {
            String[] str = dir.list();
            for(int i = 0; i < str.length; i++) {
                if(fileName.equals(str[i])) {
                    b = true;
                    break;
                }
            }
        }
        return b;
    }

    public static void writeFile(String fullPath, String content) {
    	writeFile(fullPath, content, false);
    }

    public static void writeFile(String path, String fileName, String content, boolean b) {
    	if(!path.substring(path.length()-1).equals(sep)){
    		path = path+sep;
    	}
        writeFile(path + fileName, content, b);
    }

    public static void writeFile(String fullPath, String content, boolean b) {
    	File f = new File(fullPath);
    	writeFile(f,content,b);
    }
    public static void writeFile(File f, String content, boolean b) {
        PrintWriter pw = null;
        try {
            if(!f.exists()) {
                try {
                    new File(f.getParent()).mkdirs();
                    f.createNewFile();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            pw = new PrintWriter(new FileOutputStream(f, b), true);
            pw.println(content);
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            pw.close();
        }
    }

    public static void writeFile(byte[] key,String file){
    	DataOutputStream out = null;
        try{
            //生产KEY文件
            out = new DataOutputStream(new FileOutputStream(file));
            out.write(key);
            System.out.println("KEY:"+file);
        }catch(IOException e){
            e.printStackTrace();
        }finally{
        	try{out.close();}catch(Exception e){e.printStackTrace();}
        }
    }

    public static String readTextFile(String fullPath) {
    	return readTextFile(fullPath,defaultEncoding);
    }

    public static String readTextFile(String fullPath,String encoding) {
        StringBuffer fileContent = new StringBuffer("");
        BufferedReader br = null;
        try {
            if(!new File(fullPath).exists()) {
                try {
                    return null;
                } catch(Exception e) {
                    e.printStackTrace();
                }
            } else {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(fullPath),encoding));
                String line = br.readLine();
                while(line != null) {
                    fileContent.append(line+"\n");
                    line = br.readLine();
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return fileContent.toString();
    }

    public static String loadBaseFile(String fullPath) {
        StringBuffer fileContent = new StringBuffer(FILE_CONTENT_SIZE);
        BufferedInputStream bi = null;
        try {
            if(!new File(fullPath).exists()) {
                try {
                    return null;
                } catch(Exception e) {
                    e.printStackTrace();
                }
            } else {
                bi = new BufferedInputStream(new FileInputStream(fullPath));
                byte[] buffer = new byte[FILE_BYTE_SIZE];
                int len = 0;
                while((len = bi.read(buffer)) > 0) {
                    fileContent.append(new String(buffer, 0, len, "ISO-8859-1"));
                }
            }
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if(bi != null) {
                    bi.close();
                }
            } catch(IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
        return fileContent.toString();
    }
    

    public static byte[] readFile(String filePath)throws FileNotFoundException,IOException{
    	File f = new File(filePath);  
        if(!f.exists()){  
            throw new FileNotFoundException(filePath);  
        }  
  
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int)f.length());  
        BufferedInputStream in = null;  
        try{  
            in = new BufferedInputStream(new FileInputStream(f));  
            int buf_size = FILE_BYTE_SIZE;  
            byte[] buffer = new byte[buf_size];  
            int len = 0;  
            while(-1 != (len = in.read(buffer,0,buf_size))){  
                bos.write(buffer,0,len);  
            }             
        }catch (IOException e) {  
            e.printStackTrace();  
            throw e;  
        }finally{  
            try{  
                in.close();  
            }catch (IOException e) {  
                e.printStackTrace();  
            }  
            bos.close();  
        }
        return bos.toByteArray();  
    }

    public static String readFileByClassPath(String fileClassPath)throws IOException{
		Reader reader = null;
		InputStream inputStream = null;
		StringBuffer sb = new StringBuffer("");
		char[] buffer = new char[FILE_BYTE_SIZE];
		try {
			inputStream = FileUtil.class.getResourceAsStream(fileClassPath);
			reader = new InputStreamReader(inputStream); 
			int dataLen = reader.read(buffer);
			while(dataLen>=0){
				sb.append(new String(buffer, 0, dataLen));
				dataLen = reader.read(buffer);
			}
		}finally{
			try{
				inputStream.close();
				reader.close();
			}catch(IOException ioe){}
		}
		return sb.toString();    	
    }

    public static String readFile(InputStream is)throws IOException{
		Reader reader = null;
		StringBuffer sb = new StringBuffer("");
		char[] buffer = new char[FILE_BYTE_SIZE];
		try {
			reader = new InputStreamReader(is); 
			int dataLen = reader.read(buffer);
			while(dataLen>=0){
				sb.append(new String(buffer, 0, dataLen));
				dataLen = reader.read(buffer);
			}
		}finally{
			try{
				is.close();
				reader.close();
			}catch(IOException ioe){}
		}
		return sb.toString();      	
    }

    public static byte[] loadFile(String file){
        byte[] key = null;
        try{
            FileInputStream in = new FileInputStream(file);
            key = new byte[in.available()];
            in.read(key);
            in.close();
        }catch(Exception e){e.printStackTrace();}
        return key;
    }

	public static void delDir(File dir){
		if(null != dir){
			if(dir.isDirectory()){
				String[] childrens = dir.list();
				for(int i = 0;i < childrens.length;i++){
					delDir(new File(dir,childrens[i]));
				}
			}
			dir.delete();
		}
	}
	public static Properties getProperties(File f)throws IOException{
		return getProperties(f,defaultEncoding);
	}
	public static Properties getProperties(File f,String encoding)throws IOException{
		return getProperties(new FileInputStream(f),encoding);
	}
	public static Properties getProperties(InputStream is)throws IOException{
		return getProperties(is,defaultEncoding);
	}	
	public static Properties getProperties(InputStream is,String encoding)throws IOException{
		Reader reader = null;
		Properties p = null;
		try{
			p = new Properties();
			reader = new BufferedReader(new InputStreamReader(is,encoding));
			p.load(reader);	
		}finally{
    		try{reader.close();}
    		catch(IOException ioe){}
		}
		return p;
	}
	public static String getHash(String fileName){
		return getHash(fileName,HASH_TYPE);
	}
	public static String getHash(File f){
		return getHash(f,HASH_TYPE);
	}
	public static String getHash(String fileName, String hashType) {		
		return getHash(new File(fileName),hashType);
	}

	public static String getHash(File f, String hashType) {
		if(!f.exists()){return null;}
		InputStream fis=null;
		byte[] b=null;
		byte[] buffer = new byte[1024];
		try {
			fis = new FileInputStream(f);		
			MessageDigest md5 = MessageDigest.getInstance(hashType);
			int numRead = 0;
			while ((numRead = fis.read(buffer)) > 0) {
				md5.update(buffer, 0, numRead);
			}
			b = md5.digest();
		} catch (Exception e) {
		} finally {
			try{fis.close();}catch(IOException ioe){}
		}
		String s = toHexString(b);
		return s;
	}

	public static void write(InputStream is,OutputStream os,int bufferSize) throws IOException {
        try {  
            byte[] buff = new byte[bufferSize];  
            int bytesRead;  
            while (-1 != (bytesRead = is.read(buff, 0, buff.length))) {  
                os.write(buff, 0, bytesRead);  
            }  
        } finally {  
            if (is != null)  is.close();  
            if (os != null)  os.close();  
        }		
	}

	public static void write(InputStream is,OutputStream os) throws IOException {
		write(is,os,FILE_CACHE_SIZE);
	}	

	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
			sb.append(hexChar[b[i] & 0x0f]);
		}
		return sb.toString();
	} 
}
