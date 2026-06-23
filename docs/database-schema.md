# Table

### User

- id
- username
- email
- password
- role
- isEmailVerified
- createdAt
- updatedAt

### Event

- id
- title
- description
- venue
- dateAndTime
- bannerImgUrl
- eventStatus
- createdAt
- updatedAt
- createdBy 

### EventRegistration

- id
- registeredAt
- registrationStatus
- user 
- event 

# Relationship

- One User -> Many Event
- One User -> Many Event Registration
- One Event -> Many Event Registration

Unique(user_id, event _id)
