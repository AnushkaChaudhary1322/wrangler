# Parse as Double

The `parse-as-double` directive parses the values in the specified column as **doubles** (Java `double`), converting string inputs into numeric form.

If the value cannot be parsed—such as containing invalid characters, non-numeric values, or being out of range—an error is raised.

---

## Syntax

```
parse-as-double <column>
```

- `<column>`: The name of the column containing the value to be parsed as a double.

---

## Usage Notes

- This directive parses string representations of numbers that can include decimal points (e.g., `"123.45"` or `"  456.78 "`).
- Leading and trailing whitespaces are trimmed before parsing.
- Values with non-numeric content (e.g., `"abc"`, `"NaN"`, or `"infinity"`) will fail and result in an error.
- Internally uses Java's `Double.parseDouble()`, so accepted values must be within the range of `Double.MAX_VALUE` (1.7976931348623157E308) to `Double.MIN_VALUE` (4.9E-324).
- If you only need to parse integers, use `parse-as-integer` instead.

---

## Example

Given this record:

```
{ "input": ["123.45", "456", "789.0", "abc", "1000.99"] }
```

Applying this directive:

```
parse-as-double input
```

Would result in:

```
{ "input": [123.45, 456.0, 789.0, error, 1000.99] }
```

---
