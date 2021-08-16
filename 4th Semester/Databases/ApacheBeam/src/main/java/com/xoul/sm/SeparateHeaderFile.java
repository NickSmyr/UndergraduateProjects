package com.xoul.sm;
import java.util.*;
import java.io.*;
public class SeparateHeaderFile{
  		public ArrayList<String> header;
  		public String headerlessFile;
  		public SeparateHeaderFile(ArrayList<String> header, String headerlessFile){
			this.header = header;
			this.headerlessFile = headerlessFile;
		}
}
