public class Matrix {

//    if(isInvalidMatrix(A)) {
//        return new float[][] {{Float.NaN}};
//    }

    public static float[][] rowEcholon(float A[][]) {
        for(int r=0, c=0; r < A.length && c < A[0].length; r++, c++) {
            if(A[r][c] == 0) {
                int q = r+1;
                while(q<A.length) {
                    if(A[q][c] != 0) {
                        float tmp[] = A[r];
                        A[r] = A[q];
                        A[q] = tmp;
                        break;
                    }
                    q++;
                }
                if(q == A.length) {
                    continue;
                }
            }
            for(int i=r+1; i < A.length; i++) {
                float multiplier = A[i][c] / A[r][c];
                A[i][c] = 0;
                for(int j=c+1; j < A[0].length; j++) {
                    A[i][j] = A[i][j] - (multiplier * A[r][j]);
                }
            }
        }
        return A;
    }

    public static float[][] reducedRowEcholon(float A[][]) {
        for(int r=0, c=0; r < A.length && c < A[0].length; r++, c++) {
            if(A[r][c] == 0) {
                int q = r+1;
                while(q<A.length) {
                    if(A[q][c] != 0) {
                        float tmp[] = A[r];
                        A[r] = A[q];
                        A[q] = tmp;
                        break;
                    }
                    q++;
                }
                if(q == A.length) {
                    continue;
                }
            }
            for(int i=0;i<A.length;i++) {
                if(i == r || A[i][c] == 0) {
                    continue;
                }
                float multiplier = A[i][c] / A[r][c];
                A[i][c] = 0;
                for(int j=c+1; j < A[0].length; j++) {
                    A[i][j] = A[i][j] - (multiplier * A[r][j]);
                }
            }
        }
        for(int r=0, d=0; r < A.length && d < A[0].length; r++, d++) {
            if(A[r][d] == 0) {
                continue;
            }
            for(int n=d+1; n < A[0].length; n++) {
                A[r][n] = A[r][n] / A[r][d];
            }
            A[r][d] = 1;
//            System.out.println(A[r][d]);
//            A[r][d] = A[r][d] / A[r][d];
//            System.out.println(A[r][d]+"\n");
        }
        return A;
    }

    // Laplace expansion
    public static float determinantLaplace(float A[][]) {
        if(A.length != A[0].length) {
            return Float.NaN;
        }
        if(A.length == 2) {
            return get2x2Det(A);
        }
        float det = 0;
        for(int i=0; i < A.length; i++) {
            float subMatrix[][] = new float[A.length-1][A.length-1];
            for(int row=0; row < A.length-1; row++) {
                int tmp = 0;
                for(int col=0; col < A.length; col++) {
                    if(col == i) {
                        continue;
                    }
//                    subMatrix[row][col] = A[1+row][(i+1+col)%(A.length)];
                    subMatrix[row][tmp] = A[1+row][col];
                    tmp++;
                }
            }
            if(i%2 == 0) {
                det = det + (A[0][i]* determinantLaplace(subMatrix));
            }
            else {
                det = det - (A[0][i]* determinantLaplace(subMatrix));
            }
        }
        return det;
    }

    private static float get2x2Det(float mat[][]) {
        if(mat.length > 2 || mat.length!=mat[0].length) {
//            System.out.println("Hmmmm...");
            return Float.NaN;
        }
        return (mat[1][1]*mat[0][0]) - (mat[1][0]*mat[0][1]);
    }

    public static float[][] multiply(float A[][], float B[][]) {
        if(B.length < 1 || A[0].length != B.length) {
            System.err.println("Invalid matrix or column count of first is NOT equal to row count of second");
            return new float[][] {{Float.NaN}};
        }
        float result[][] = new float[A.length][B[0].length];
        for(int r=0; r < A.length; r++) {
            for(int k=0; k < B[0].length; k++) {
                float dot = 0;
                for(int c=0; c < A[0].length; c++) {
                    dot = dot + (A[r][c]*B[c][k]);
                }
                result[r][k] = dot;
            }
        }
        return result;
    }

    public static float[][] add(float A[][], float B[][]) {
        if(!isSameDimension(A, B)) {
            System.err.println("Invalid matrix or unequal dimension");
            return new float[][] {{Float.NaN}};
        }
        for(int row=0; row < A.length; row++) {
            for(int col=0; col < A[0].length; col++) {
                A[row][col] = A[row][col] + B[row][col];
            }
        }
        return A;
    }

    public static float[][] transpose(float A[][]) {
        float result[][] = new float[A[0].length][A.length];
        for(int r=0; r<A.length; r++) {
            for(int c=0; c<A[0].length; c++) {
                result[c][r] = A[r][c];
            }
        }
        return result;
    }

    public static boolean isInvalidMatrix(float A[][]) {
        return A.length == 0 || A[0].length == 0;
    }

    public static boolean isSameDimension(float A[][], float B[][]) {
        return A.length == B.length && A[0].length == B[0].length;
    }

    public static boolean isSquare(float A[][]) {
        return A.length == A[0].length;
    }

    public static boolean isSame(float A[][], float B[][]) {
        if(!isSameDimension(A, B)) {
            return false;
        }
        for(int row=0;row<A.length;row++) {
            for(int col=0;col<A[row].length;col++) {
                if(A[row][col] != B[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void printMatrix(float A[][]) {
        for(int i=0; i < A[0].length;i++) {
            System.out.print("------");
        }
        System.out.println();
        for(int row=0;row<A.length;row++) {
            System.out.print("|  ");
            for(int col=0;col<A[row].length;col++) {
//                System.out.print(String.valueOf(A[row][col]+0.0)+"  ");
//                System.out.print(String.valueOf(A[row][col])+"  ");
                System.out.print(java.math.BigDecimal.valueOf(A[row][col]).setScale(2, java.math.BigDecimal.ROUND_HALF_UP)+"  ");
            }
            System.out.print("|\n");
        }
        for(int i=0; i < A[0].length;i++) {
            System.out.print("------");
        }
        System.out.println();
    }
}
