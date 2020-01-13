public class LongestPath {
    public static void main(String[] args) {
        Graph graph = new Graph(6);
        graph.addEdge(1, 2, 5);
        graph.addEdge(1, 3, 3);
        graph.addEdge(2, 4, 6);
        graph.addEdge(2, 3, 2);
        graph.addEdge(3, 5, 4);
        graph.addEdge(3, 6, 2);
        graph.addEdge(3, 4, 7);
        graph.addEdge(4, 6, 1);
        graph.addEdge(4, 5, -1);
        graph.addEdge(5, 6, -2);
        graph.findLongestPath(2);

    }

    public static class Graph {
        Vertex[] vertices;
        int size;
        int maxSize;
        Stack stack;

        public Graph(int maxSize) {
            vertices = new Vertex[maxSize];
            this.maxSize = maxSize;
            stack = new Stack(maxSize);
            for (int i = 0; i < maxSize; i++) {
                addVertex(i + 1);
            }
        }

        public void addVertex(int data) {
            vertices[size++] = new Vertex(data);
        }

        public void addEdge(int source, int destination, int weight) {
            vertices[source - 1].adj = new Neighbour(destination - 1, weight, vertices[source - 1].adj);
        }

        public class Vertex {
            int data;
            Neighbour adj;
            int cost =  Integer.MIN_VALUE;
            State state = State.NEW;

            public Vertex(int data) {
                this.data = data;
            }
        }

        public class Stack {
            Vertex[] stack;
            int maxSize;
            int size;

            public Stack(int maxSize) {
                this.maxSize = maxSize;
                stack = new Vertex[maxSize];
            }

            public void push(Vertex data) {
                stack[size++] = data;
            }

            public Vertex pop() {
                return stack[--size];
            }

            public boolean isEmpty() {
                return size == 0;
            }
        }

        public enum State {
            NEW, VISITED
        }

        public class Neighbour {
            int index;
            Neighbour next;
            int weight;

            Neighbour(int index, int weight, Neighbour next) {
                this.index = index;
                this.next = next;
                this.weight = weight;
            }
        }

        public void findLongestPath(int source) {
            applyTopologicalSort();
            vertices[source-1].cost = 0;
            while (!stack.isEmpty()) {
                Vertex u = stack.pop();
                if(u.cost != Integer.MIN_VALUE){
                    Neighbour temp = u.adj;
                    while(temp != null){
                        Vertex v = vertices[temp.index];
                        if(v.cost < (temp.weight+u.cost)){
                            v.cost = temp.weight+u.cost;
                        }
                        temp = temp.next;
                    }
                }
            }
            System.out.println("Longest distances...");
            
            for(int i = 0; i < maxSize; i++){
                System.out.println("Longest path from 2 to " + (i+1)+ " "+vertices[i].cost);
            }
        }

        private void applyTopologicalSort() {
            for (int i = 0; i < maxSize; i++) {
                if (vertices[i].state != State.VISITED) {
                    dfs(vertices[i]);
                }
            }
        }

        public void dfs(Vertex u) {
            Neighbour temp = u.adj;
            u.state = State.VISITED;
            while (temp != null) {
                Vertex v = vertices[temp.index];
                if (v.state == State.NEW) {
                    dfs(v);
                }
                temp = temp.next;
            }
            stack.push(u);
        }
    }

}
