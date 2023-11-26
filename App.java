import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        // Parte 1 definicion de variables
        int numeroProposiciones;
        String[][] tablaVerdad;
        int tamFilas;
        Scanner ingresoDatos = new Scanner(System.in);
        System.out.print("Ingrese el numero de proposiciones: ");
        numeroProposiciones = Integer.parseInt(ingresoDatos.nextLine());
        // Numero de combinaciones posibles, sin repeticion, con el numero de
        // proposiciones
        int numeroCombinacionesProposiciones = factorial(numeroProposiciones)
                / (factorial(numeroProposiciones - 2) * 2);
        int numerOperaciones = numeroProposiciones + numeroCombinacionesProposiciones * 4;
        tablaVerdad = new String[1 + (int) Math.pow(2, numeroProposiciones)][numeroProposiciones + numerOperaciones];
        // Tamanio de las filas
        tamFilas = 1 + (int) Math.pow(2, numeroProposiciones);
        // Para poner los nombres en las columnas con las operaciones que se hacen
        char nombreProposicion = 97;
        // :;ena la tabla con los valores de verdad
        tablaVerdad = llenarTabla(tablaVerdad, numeroProposiciones, tamFilas, 0, nombreProposicion);
        // Operaciones
        negacion(tablaVerdad, 0, numeroProposiciones, numeroProposiciones);
        disyuncion(tablaVerdad, 0, numeroCombinacionesProposiciones, 0, 1, numeroProposiciones,
                numeroProposiciones * 2);
        conjuncion(tablaVerdad, 0, numeroCombinacionesProposiciones, 0, 1, numeroProposiciones,
                numeroProposiciones * 2 + numeroCombinacionesProposiciones);
        imprimirTablaVerdad(tablaVerdad);
    }

    /**
     * Este metodo me ayudara a imprimir la tabla de valor de verdad
     * 
     * @param tablaVerdad
     */
    public static void imprimirTablaVerdad(String[][] tablaVerdad) {
        for (int i = 0; i < tablaVerdad.length; i++) {
            for (int j = 0; j < tablaVerdad[0].length; j++) {
                System.out.print(tablaVerdad[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Metodo para llenar la tabla de verdad (metodo recursivo)
     * 
     * @param tablaVerdad
     * @param numeroProposiciones
     * @param tamFilas
     * @param columnaLlenar
     * @param nombreProposicion
     * @return
     */
    public static String[][] llenarTabla(String[][] tablaVerdad, int numeroProposiciones, int tamFilas,
            int columnaLlenar,
            char nombreProposicion) {
        // Variable utilizada para reiniciar el patron de V y F
        int reiniciador = 0;
        // Pone el nombre a la columna
        tablaVerdad[0][columnaLlenar] = String.valueOf(nombreProposicion);
        // Verifica si se sobrepasa las columnas a llenar para que se pueda parar la
        // recursividad
        if (columnaLlenar < numeroProposiciones) {
            // Recorre la columna para llenar
            for (int i = 1; i < tablaVerdad.length; i++) {
                // Verificamos si supero el numero de filas al patron de llenado de cada columna
                if (i - reiniciador > ((tamFilas) / Math.pow(2, columnaLlenar))) {
                    // Si se paso, a reiniciador le ponemos el valor de i - 1, para que inicie de
                    // nuevo el conteo
                    reiniciador = i - 1;
                }
                // Verifica si se debe llenar con V si no sobrepasa el patron de esa columna, si
                // sobrepasa se llena con F
                if (i - reiniciador < ((tamFilas) / Math.pow(2, columnaLlenar + 1))) {
                    tablaVerdad[i][columnaLlenar] = "V";
                } else {
                    tablaVerdad[i][columnaLlenar] = "F";
                }
            }
            // Recursividad
            llenarTabla(tablaVerdad, numeroProposiciones, tamFilas, ++columnaLlenar, ++nombreProposicion);
        }
        return tablaVerdad;
    }

    /**
     * Calcula la negacion (metodo recursivo)
     * 
     * @param tablaVerdad
     * @param columna
     * @param numeroProposiciones
     * @param columnaResultado
     * @return
     */
    public static String[][] negacion(String[][] tablaVerdad, int columna, int numeroProposiciones,
            int columnaResultado) {
        // Las columnas son las proposiciones a negar, sirve para obtener la columna
        if (numeroProposiciones > columna) {
            // Pone el nombre a la columna
            tablaVerdad[0][columnaResultado] = "¬" + tablaVerdad[0][columna];
            // Recorre la columna a negar y la niega
            for (int i = 1; i < tablaVerdad.length; i++) {
                if (tablaVerdad[i][columna].equals("V")) {
                    tablaVerdad[i][columnaResultado] = " F";
                } else {
                    tablaVerdad[i][columnaResultado] = " V";
                }
            }
            // Recursividad, para negar todas la proposiciones
            negacion(tablaVerdad, ++columna, numeroProposiciones, ++columnaResultado);
        }
        return tablaVerdad;
    }

    /**
     * calcula el factorial de numeros para sacar el numero de condiciones posibles
     * 
     * @param numeroProposiciones
     * @return
     */
    public static int factorial(int numeroProposiciones) {
        if (numeroProposiciones == 0) {
            return 1;
        } else {
            return numeroProposiciones * factorial(numeroProposiciones - 1);
        }
    }

    /**
     * Calcula la disyuncion (metodo recursivo)
     * 
     * @param tablaVerdad
     * @param columnas
     * @param numeroCombinacionesProposiciones
     * @param proposicion1
     * @param proposicion2
     * @param numeroProposiciones
     * @param columnaResultado
     * @return
     */
    public static String[][] disyuncion(String[][] tablaVerdad, int columnas, int numeroCombinacionesProposiciones,
            int proposicion1, int proposicion2, int numeroProposiciones, int columnaResultado) {
        // Verifico si se lleno las columnas de los resultados
        if (numeroCombinacionesProposiciones > columnas) {
            // Pone nombre a la columna
            tablaVerdad[0][columnaResultado] = tablaVerdad[0][proposicion1] + "v" + tablaVerdad[0][proposicion2];
            // Recorro las filas de las proposiciones
            for (int i = 1; i < tablaVerdad.length; i++) {
                if (tablaVerdad[i][proposicion1].equals("V") || tablaVerdad[i][proposicion2].equals("V")) {
                    tablaVerdad[i][columnaResultado] = "  V";
                } else {
                    tablaVerdad[i][columnaResultado] = "  F";
                }
            }
            // Verifica si se acabo de comparar una columna con todas las demas
            if (proposicion2 < numeroProposiciones - 1) {
                // Si no acabo entonces solo se afecta la proposicion 2
                proposicion2++;
            } else {
                // Si acabo mueve una posicion a la proposicion1 y se redefine la proposicion2
                // para que este uno encima de la 1 y no se comparen las mismas columnas
                proposicion1++;
                proposicion2 = proposicion1 + 1;
            }
            // Recursividad, aumenta el numero de columnas para controlar el numero de
            // operaciones hechas y las columnas del resultado
            return disyuncion(tablaVerdad, ++columnas, numeroCombinacionesProposiciones, proposicion1, proposicion2,
                    numeroProposiciones, ++columnaResultado);
        }
        return tablaVerdad;
    }

    /**
     * Calcula la conjuncion (metodo recursivo)
     * 
     * @param tablaVerdad
     * @param columnas
     * @param numeroCombinacionesProposiciones
     * @param proposicion1
     * @param proposicion2
     * @param numeroProposiciones
     * @param columnaResultado
     * @return
     */
    public static String[][] conjuncion(String[][] tablaVerdad, int columnas, int numeroCombinacionesProposiciones,
            int proposicion1, int proposicion2, int numeroProposiciones, int columnaResultado) {
        // Verifico si se lleno las columnas de los resultados
        if (numeroCombinacionesProposiciones > columnas) {
            // Pone nombre a la columna
            tablaVerdad[0][columnaResultado] = tablaVerdad[0][proposicion1] + "^" + tablaVerdad[0][proposicion2];
            // Recorro las filas de las proposiciones
            for (int i = 1; i < tablaVerdad.length; i++) {
                if (tablaVerdad[i][proposicion1].equals("V") && tablaVerdad[i][proposicion2].equals("V")) {
                    tablaVerdad[i][columnaResultado] = "  V";
                } else {
                    tablaVerdad[i][columnaResultado] = "  F";
                }
            }
            // Verifica si se acabo de comparar una columna con todas las demas
            if (proposicion2 < numeroProposiciones - 1) {
                // Si no acabo entonces solo se afecta la proposicion 2
                proposicion2++;
            } else {
                // Si acabo mueve una posicion a la proposicion1 y se redefine la proposicion2
                // para que este uno encima de la 1 y no se comparen las mismas columnas
                proposicion1++;
                proposicion2 = proposicion1 + 1;
            }
            // Recursividad, aumenta el numero de columnas para controlar el numero de
            // operaciones hechas y las columnas del resultado
            return conjuncion(tablaVerdad, ++columnas, numeroCombinacionesProposiciones, proposicion1, proposicion2,
                    numeroProposiciones, ++columnaResultado);
        }
        return tablaVerdad;
    }
}
