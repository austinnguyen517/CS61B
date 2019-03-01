public class UnionFind {

    int[] parentsOfInd;
    int size;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        parentsOfInd = new int[n];
        size = n;
        for (int i = 0; i < n; i += 1) {
            parentsOfInd[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex >= size || vertex < 0) {
            throw new IndexOutOfBoundsException("Vertex not in this set");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        return 1 + numChildren(v1) + numParents(v1);
    }

    private int numChildren(int v) {
        int count = 0;
        for (int i = 0; i < size; i += 1) {
            if (parentsOfInd[i] == v) {
                count += 1;
                count += numChildren(i);
            }
        }
        return count;
    }

    private int numParents(int v) {
        if (parentsOfInd[v] == -1) {
            return 0;
        } else {
            return 1 + numParents(parentsOfInd[v]);
        }
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        int par = parentsOfInd[v1];
        if (par == -1) {
            return -1 * sizeOf(v1);
        }
        return par;
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        if (!connected(v1, v2)) {
            if (sizeOf(v1) > sizeOf(v2)) {
                parentsOfInd[find(v2)] = find(v1);
            } else {
                parentsOfInd[find(v1)] = find(v2);
            }
        }
    }


    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        if (parentsOfInd[vertex] == -1) {
            return vertex;
        } else {
            return find(parentsOfInd[vertex]);
        }
    }

}
