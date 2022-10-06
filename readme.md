# Log Parser

An application to extract log data as CSV file.

```text
11:26:43.069 [main] INFO com.vscoding.tutorial.control.Importer - Result: 'Jobs' created='2', deleted='15'
11:26:43.069 [main] INFO com.vscoding.tutorial.control.Importer - Result: 'Assets' created='50', deleted='12'
```

```csv
name;created;deleted
"Jobs";2;15
"Assets";50;12
```
