import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class Polynomial {
    double[] coefficients; // field
    int[] exponents;

    public Polynomial() {
        coefficients = new double[1];
        exponents = new int[1];
        coefficients[0] = 0;
        exponents[0] = 0;
    }

    public Polynomial(double[] a, int[] b) {
        coefficients = a;
        exponents = b;
    }

    public Polynomial add(Polynomial a) {
        int degree = Math.max(a.coefficients.length, coefficients.length);
        double[] add_poly = new double[degree];
        int[] exponents = new int[degree];

        for (int i = 0; i < add_poly.length; i++) {
            exponents[i] = i;
            if (i < a.coefficients.length) {
                add_poly[i] += a.coefficients[i];
            }
            if (i < coefficients.length) {
                add_poly[i] += coefficients[i];
            }
        }
        Polynomial new_poly = new Polynomial(add_poly, exponents);
        return new_poly;
    }

    public double evaluate(double x) {
        double sum = 0.0;

        for (int i = 0; i < coefficients.length; i++) {
            sum += (coefficients[i] * Math.pow(x, exponents[i]));
        }
        return sum;
    }
    
    public boolean hasRoot(double x) {
        if (evaluate(x) == 0) {
            return true;
        }
        return false;
    }

    public Polynomial multiply(Polynomial a) {
        int maxSize = a.coefficients.length * coefficients.length;
        double[] maxCoeff = new double[maxSize];
        int[] maxExp = new int[maxSize];
        boolean flag = false;
        int val = 0;

        for (int i = 0; i < coefficients.length; i++) {
            for (int j = 0; j < a.coefficients.length; j++) {
                double newCoeff = coefficients[i] * a.coefficients[j];
                int newExp = exponents[i] + a.exponents[j];
                flag = false;
                for (int k = 0; k < val; k++) {
                    if (maxExp[k] == newExp) {
                        maxCoeff[k] += newExp;
                        flag = true;
                        break;
                    }
                }
                if (flag == false) {
                    maxCoeff[val] = newCoeff;
                    maxExp[val] = newExp;
                    val++;
                }

            }
        }

        double[] resultCoeff = new double[val];
        int[] resultExp = new int[val];
        for (int l = 0; l < val; l++) {
            resultCoeff[l] = maxCoeff[l];
            resultExp[l] = maxExp[l];
        }
        Polynomial result = new Polynomial(resultCoeff, resultExp);
        return result;
    }

    public Polynomial(File file) {
        String line = "";
        try {
        BufferedReader input = new BufferedReader(new FileReader(file));
        line = input.readLine();
        input.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        
        ArrayList<Double> newCoeff = new ArrayList<>();
        ArrayList<Integer> newExp = new ArrayList<>();

        for (int i = 1; i < line.length(); i++) {
            if (line.charAt(i) == '-') {
                line = line.substring(0, i) + '+' + line.substring(i);
                i++;
            }
        }
        String[] split = line.split("\\+");
        for (int j = 0; j < split.length; j++) {
            String val = split[j];
            double coeff = 0.0;
            int exp = 0;
            if (val.contains("x")) {
                String[] splitExp = val.split("x");
                if (splitExp[0] == "" || splitExp[0] == "+") {
                    coeff = 1;
                }
                else if (splitExp[0] == "-") {
                    coeff = -1;
                }
                else {
                    coeff = Double.parseDouble(splitExp[0]);
                }
                if (splitExp.length > 1 && splitExp[1] != "") {
                    exp = Integer.parseInt(splitExp[1]);
                } else {
                    exp = 1;
                }
            } else {
                coeff = Double.parseDouble(val);
            }
            newCoeff.add(coeff);
            newExp.add(exp);
        }
        coefficients = new double[newCoeff.size()];
        exponents = new int[newExp.size()];
        for (int k = 0; k < newCoeff.size(); k++) {
            coefficients[k] = newCoeff.get(k);
        }
        for (int l = 0; l < newCoeff.size(); l++) {
            exponents[l] = newExp.get(l);
        }

    }

    public void saveToFile(String fileName) {
        String polynomial = "";
        
        for (int i = 0; i < coefficients.length; i++) {
            int coeff = (int)coefficients[i];
            int exp = exponents[i];

            if (i > 0) {
                if (coeff > 0) {
                    polynomial += "+";
                }
            }

            if (exp == 0) {
                polynomial += coeff;
            } else if (exp == 1) {
                if (coeff == 1) {
                    polynomial += "x";
                } else if (coeff == -1) {
                    polynomial += "-x";
                } else {
                    polynomial += coeff + "x";
                }
            } else {
                if (coeff == 1) {
                    polynomial += "x" + exp;
                } else if (coeff == -1) {
                    polynomial += "-x" + exp; 
                } else {
                    polynomial += coeff + "x" + exp;
                }
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(polynomial);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    }

}