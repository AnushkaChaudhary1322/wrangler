---
### `parse-as-timeduration.md`
---

# Parse as Time Duration

The `parse-as-timeduration` directive parses the values in the specified column as **time durations**, converting string representations of durations (e.g., `"10s"`, `"1m"`, `"2h"`) into a numeric format.

If the value cannot be parsed (e.g., invalid format or missing unit), an error is raised.

---

## Syntax

```
parse-as-timeduration <column>
```

- `<column>`: The name of the column containing the value to be parsed as a time duration.

---

## Usage Notes

- This directive supports various time units like seconds (`s`), minutes (`m`), hours (`h`), and days (`d`).
- The time value must be a valid number followed by one of the units.
- Internally uses Java's `Duration.parse()` for parsing, and the duration will be represented as a number in milliseconds.
- If the value is in an invalid format, an error will be thrown.

---

## Example

Given this record:

```
{ "input": ["10s", "5m", "2h", "abc", "3d"] }
```

Applying this directive:

```
parse-as-timeduration input
```

Would result in:

```
{ "input": [10000, 300000, 7200000, error, 259200000] }
```
