
# Fake Jira server

Sample commands:
```bash

# List tickets
curl localhost:9191/tickets
# Create new ticket
curl -X POST -d 'TITLE' localhost:9191/tickets

ID=64752379-7b43-4dc1-9225-7f841143323a
# Get ticket by ID
curl localhost:9191/tickets/$IS

# Approve ticket by ID
curl -X PUT localhost:9191/tickets/$ID/approve
```