---
### `parse-as-bytesize.md`
---

# Parse as Byte Size

The `parse-as-bytesize` directive parses the values in the specified column as **byte sizes**, converting string representations of sizes (e.g., `"10KB"`, `"1MB"`, `"2GB"`) into numeric values in bytes.

If the value cannot be parsed (e.g., invalid format or missing unit), an error is raised.

---

## Syntax

```
parse-as-bytesize <column>
```

- `<column>`: The name of the column containing the value to be parsed as a byte size.

---

## Usage Notes

- This directive supports various byte units like bytes (`B`), kilobytes (`KB`), megabytes (`MB`), gigabytes (`GB`), etc.
- The byte size value must be a valid number followed by one of the units.
- Internally, the directive converts the value into the corresponding size in bytes.
- If the value is in an invalid format or the unit is not recognized, an error will be thrown.

---

## Example

Given this record:

```
{ "input": ["10KB", "1MB", "2GB", "abc", "500B"] }
```

Applying this directive:

```
parse-as-bytesize input
```

Would result in:

```
{ "input": [10240, 1048576, 2147483648, error, 500] }
```

---
