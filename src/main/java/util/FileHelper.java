package util;

import java.io.File;

public class FileHelper {
  public void deleteRepository(File r) {
    if(r.isDirectory()){
      delete(r);
    }
    r.delete();
  }
  public void delete(File r){
    File [] fileList = r.listFiles();
    for(int i = 0;i<fileList.length;i++){
      if(fileList[i].isDirectory() ){
        delete(fileList[i]);
        fileList[i].delete();
      }else{
        fileList[i].delete();
      }
    }
  }
}