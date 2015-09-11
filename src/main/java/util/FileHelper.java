/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
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