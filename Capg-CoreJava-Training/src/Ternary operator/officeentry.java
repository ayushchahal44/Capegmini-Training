class officeentry {
    public static void main(String[] args) {

        boolean idcard = true;
        boolean permanent = true;
        boolean contract = false;
        boolean approved = false;

        System.out.println(
            idcard
                ? (permanent || (contract && approved)
                    ? "You can Enter."
                    : "Please get approval from your manager.")
                : "You cannot enter."
        );
    }
}
