class Solution {

    private int idx = 0;

    public List<String> braceExpansionII(String expression) {
        Set<String> result = parseExpr(expression);

        List<String> ans = new ArrayList<>(result);
        Collections.sort(ans);
        return ans;
    }

    // expr := term (, term)*
    private Set<String> parseExpr(String s) {
        Set<String> result = parseTerm(s);

        while (idx < s.length() && s.charAt(idx) == ',') {
            idx++; // skip comma
            result.addAll(parseTerm(s));
        }

        return result;
    }

    // term := factor*
    private Set<String> parseTerm(String s) {
        Set<String> result = new HashSet<>();
        result.add("");

        while (idx < s.length()
                && s.charAt(idx) != '}'
                && s.charAt(idx) != ',') {

            Set<String> factor = parseFactor(s);
            result = concatenate(result, factor);
        }

        return result;
    }

    // factor := letter | { expr }
    private Set<String> parseFactor(String s) {
        Set<String> result = new HashSet<>();

        char ch = s.charAt(idx);

        if (Character.isLowerCase(ch)) {
            result.add(String.valueOf(ch));
            idx++;
        } else { // '{'
            idx++; // skip {

            result = parseExpr(s);

            idx++; // skip }
        }

        return result;
    }

    private Set<String> concatenate(Set<String> a, Set<String> b) {
        Set<String> result = new HashSet<>();

        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }

        return result;
    }
}