class candidate {
    public static void main(String[] args) {

        boolean coding = true;
        boolean logic = true;
        boolean referral = false;

        System.out.println(
            ((logic && coding) || referral)
                ? "You are passed."
                : (logic && !coding)
                    ? "Coding exam failed."
                    : (!logic && coding)
                        ? "Logic exam failed."
                        : "Both exams failed."
        );
    }
}
