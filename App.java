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
        
        factorial(numeroProposiciones);
        int numeroCombinacionesProposiciones = factorial(numeroProposiciones)
                / (factorial(numeroProposiciones - 2) * 2);
        int numerOperaciones = numeroProposiciones + numeroCombinacionesProposiciones * 3;
        tablaVerdad = new String[1 + (int) Math.pow(2, numeroProposiciones)][numeroProposiciones + numerOperaciones];
        tamFilas = 1 + (int) Math.pow(2, numeroProposiciones);
        char nombreProposicion = 97;
        tablaVerdad = llenarTabla(tablaVerdad, numeroProposiciones, tamFilas, 0, nombreProposicion);
        negacion(tablaVerdad, 0, numeroProposiciones, numeroProposiciones);
        disyuncion(tablaVerdad, 0, numeroCombinacionesProposiciones, 0, 1, numeroProposiciones,
                numeroProposiciones * 2);
        conjuncion(tablaVerdad, 0, numeroCombinacionesProposiciones, 0, 1, numeroProposiciones,
                numeroProposiciones * 2 + numeroCombinacionesProposiciones);
        implicacion(tablaVerdad, 0, numeroCombinacionesProposiciones, 0, 1, numeroProposiciones,
                numeroProposiciones * 2 + numeroCombinacionesProposiciones);
        dobleImplicacion(tablaVerdad, 0, numeroCombinacionesProposiciones, 0, 1, numeroProposiciones,
                numerOperaciones/*numeroProposiciones * 2 + numeroCombinacionesProposiciones*2*/);
        imprimirTablaVerdad(tablaVerdad);
    }

    // Este metodo me ayudara a imprimir la tabla de valor de verdad
    public static void imprimirTablaVerdad(String[][] tablaVerdad) {
        for (int i = 0; i < tablaVerdad.length; i++) {
            for (int j = 0; j < tablaVerdad[0].length; j++) {
                System.out.print(tablaVerdad[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static String[][] llenarTabla(String[][] tablaVerdad, int numeroProposiciones, int tamFilas,
            int columnaLlenar,
            char nombreProposicion) {
        int reiniciador = 0;
        tablaVerdad[0][columnaLlenar] = String.valueOf(nombreProposicion);
        if (columnaLlenar == numeroProposiciones) {
            for (int i = 1; i < tamFilas; i++) {
                if (i % 2 == 0) {
                    tablaVerdad[i][columnaLlenar] = "F";
                } else {
                    tablaVerdad[i][columnaLlenar] = "V";
                }
            }
        } else {
            for (int i = 1; i < tablaVerdad.length; i++) {
                if (i - reiniciador > ((tamFilas) / Math.pow(2, columnaLlenar))) {
                    reiniciador = i - 1;
                }
                if (i - reiniciador < ((tamFilas) / Math.pow(2, columnaLlenar + 1))) {
                    tablaVerdad[i][columnaLlenar] = "V";
                } else {
                    tablaVerdad[i][columnaLlenar] = "F";
                }
            }
            llenarTabla(tablaVerdad, numeroProposiciones, tamFilas, ++columnaLlenar, ++nombreProposicion);
        }
        return tablaVerdad;
    }

    public static String[][] negacion(String[][] tablaVerdad, int columna, int numeroProposiciones,
            int columnaResultado) {
        if (numeroProposiciones > columna) {
            tablaVerdad[0][columnaResultado] = "¬" + tablaVerdad[0][columna];
            for (int i = 1; i < tablaVerdad.length; i++) {
                if (tablaVerdad[i][columna].equals("V")) {
                    tablaVerdad[i][columnaResultado] = " F";
                } else {
                    tablaVerdad[i][columnaResultado] = " V";
                }
            }
            negacion(tablaVerdad, ++columna, numeroProposiciones, ++columnaResultado);
        }
        return tablaVerdad;
    }

    public static int factorial(int numeroProposiciones) {
        if (numeroProposiciones > 0) {
            return numeroProposiciones * factorial(numeroProposiciones - 1);
        }
        return 1;
    }

    public static String[][] disyuncion(String[][] tablaVerdad, int columnas, int numeroCombinacionesProposiciones,
            int proposicion1, int proposicion2, int numeroProposiciones, int columnaResultado) {
        if (numeroCombinacionesProposiciones > columnas) {
            tablaVerdad[0][columnaResultado] = tablaVerdad[0][proposicion1] + "v" + tablaVerdad[0][proposicion2];
            for (int i = 1; i < tablaVerdad.length; i++) {
                if (tablaVerdad[i][proposicion1].equals("V") || tablaVerdad[i][proposicion2].equals("V")) {
                    tablaVerdad[i][columnaResultado] = "  V";
                } else {
                    tablaVerdad[i][columnaResultado] = "  F";
                }
            }
            if (proposicion2 < numeroProposiciones - 1) {
                proposicion2++;
            } else {
                proposicion1++;
                proposicion2 = proposicion1 + 1;
            }
            return disyuncion(tablaVerdad, ++columnas, numeroCombinacionesProposiciones, proposicion1, proposicion2,
                    numeroProposiciones, ++columnaResultado);
        }
        return tablaVerdad;
    }

    public static String[][] conjuncion(String[][] tablaVerdad, int columnas, int numeroCombinacionesProposiciones,
            int proposicion1, int proposicion2, int numeroProposiciones, int columnaResultado) {
        if (numeroCombinacionesProposiciones > columnas) {
            tablaVerdad[0][columnaResultado] = tablaVerdad[0][proposicion1] + "^" + tablaVerdad[0][proposicion2];
            for (int i = 1; i < tablaVerdad.length; i++) {
                if (tablaVerdad[i][proposicion1].equals("V") && tablaVerdad[i][proposicion2].equals("V")) {
                    tablaVerdad[i][columnaResultado] = "  V";
                } else {
                    tablaVerdad[i][columnaResultado] = "  F";
                }
            }
            if (proposicion2 < numeroProposiciones - 1) {
                proposicion2++;
            } else {
                proposicion1++;
                proposicion2 = proposicion1 + 1;
            }
            return conjuncion(tablaVerdad, ++columnas, numeroCombinacionesProposiciones, proposicion1, proposicion2,
                    numeroProposiciones, ++columnaResultado);
        }
        return tablaVerdad;
    }

    public static String[][] implicacion(String[][] tablaVerdad, int columnas, int numeroCombinacionesProposiciones,
            int proposicion1, int proposicion2, int numeroProposiciones, int columnaResultado) {
        if (numeroCombinacionesProposiciones > columnas) {
            tablaVerdad[0][columnaResultado] = tablaVerdad[0][proposicion1] + "->" + tablaVerdad[0][proposicion2];
            for (int i = 1; i < tablaVerdad.length; i++) {
                if (tablaVerdad[i][proposicion1].equals("V") && tablaVerdad[i][proposicion2].equals("F")) {
                    tablaVerdad[i][columnaResultado] = "  F";
                } else {
                    tablaVerdad[i][columnaResultado] = "  V";
                }
            }
            if (proposicion2 < numeroProposiciones - 1) {
                proposicion2++;
            } else {
                proposicion1++;
                proposicion2 = proposicion1 + 1;
            }
            return implicacion(tablaVerdad, ++columnas, numeroCombinacionesProposiciones, proposicion1, proposicion2,
                    numeroProposiciones, ++columnaResultado);
        }
        return tablaVerdad;
    }
    
    public static String[][] dobleImplicacion(String[][] tablaVerdad, int columnas, int numeroCombinacionesProposiciones,
            int proposicion1, int proposicion2, int numeroProposiciones, int columnaResultado) {
        if (numeroCombinacionesProposiciones > columnas) {
            tablaVerdad[0][columnaResultado] = tablaVerdad[0][proposicion1] + "<->" + tablaVerdad[0][proposicion2];
            for (int i = 1; i < tablaVerdad.length; i++) {
                if ((tablaVerdad[i][proposicion1].equals("V") && tablaVerdad[i][proposicion2].equals("V")) ||
                            (tablaVerdad[i][proposicion1].equals("F") && tablaVerdad[i][proposicion2].equals("F"))) {
                        tablaVerdad[i][columnaResultado] = "  V";
                    } else {
                        tablaVerdad[i][columnaResultado] = "  F";
                    }
            }
            if (proposicion2 < numeroProposiciones - 1) {
                proposicion2++;
            } else {
                proposicion1++;
                proposicion2 = proposicion1 + 1;
            }

            return dobleImplicacion(tablaVerdad, ++columnas, numeroCombinacionesProposiciones, proposicion1,
                    proposicion2, numeroProposiciones, ++columnaResultado);
        }
        return tablaVerdad;
    }

}
