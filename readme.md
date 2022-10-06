# Log Parser

Parse logs and export data as CSV.

## Log
```text
11:26:43.069 [main] INFO com.vscoding.tutorial.control.Importer - Result: 'Jobs' created='2', deleted='15'
11:26:43.069 [main] INFO com.vscoding.tutorial.control.Importer - Result: 'Assets' created='50', deleted='12'
```
## CSV
| name     | created | deleted | 
|----------|---------|---------|
| "Jobs"   | 2       | 15      |
| "Assets" | 50      | 12      |

