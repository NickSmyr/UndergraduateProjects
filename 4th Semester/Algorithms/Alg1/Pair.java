public class Pair<X,Y>{
		X x;
		Y y;
		public Pair(X x, Y y){
			this.x = x;
			this.y = y;
		}
		public Pair(){}
		@Override
		public boolean equals(Object right){
			return this.x.equals(((Pair<X,Y>)right).x);
		}
		@Override
		public String toString(){
			return "(" + x + " " + y + ")";		
		}
}
