# Parse as Integer

The `parse-as-integer` directive parses the values in the specified column as **integers** (Java `int`), converting string inputs into numeric form.

If the value cannot be parsed—such as containing invalid characters, floating-point numbers, or being out of range—an error is raised.

---

## Syntax

```
parse-as-integer <column>
```

- `<column>`: The name of the column containing the value to be parsed as an integer.

---

## Usage Notes

- This directive parses standard string representations of integers, such as `"123"` or `"  456  "`.
- Leading and trailing whitespaces are trimmed before parsing.
- Values with floating-point format (e.g., `"789.0"`), alphabetical characters (e.g., `"abc"`), or invalid number formats will fail and result in an error.
- Internally uses Java's `Integer.parseInt()`, so accepted values must be within the range of -2,147,483,648 to 2,147,483,647.
- To parse larger numbers, use the `parse-as-long` directive instead.

---

## Example

Given this record:

```
{ "input": ["123", "456", "789.0", "abc", "1000"] }
```

Applying this directive:

```
parse-as-integer input
```

Would result in:

```
{ "input": [123, 456, error, error, 1000] }
```

---
