class Polynomial {
    double[] coefficients; // field

    public Polynomial() {
        coefficients = new double[1];
        coefficients[0] = 0;
    }

    public Polynomial(double[] a) {
        coefficients = a;
    }

    public Polynomial add(Polynomial a) {
        int degree = Math.max(a.coefficients.length, coefficients.length);
        double[] add_poly = new double[degree];

        for (int i = 0; i < add_poly.length; i++) {
            if (i < a.coefficients.length) {
                add_poly[i] += a.coefficients[i];
            }
            if (i < coefficients.length) {
                add_poly[i] += coefficients[i];
            }

        }
        Polynomial new_poly = new Polynomial(add_poly);
        return new_poly;
    }

    public double evaluate(double x) {
        double sum = 0.0;

        for (int i = 0; i < coefficients.length; i++) {
            sum += (coefficients[i] * Math.pow(x, i));
        }
        return sum;
    }
    
    public boolean hasRoot(double x) {
        if (evaluate(x) == 0) {
            return true;
        }
        return false;
    }
}