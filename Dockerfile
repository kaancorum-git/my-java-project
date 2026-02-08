# Use the official Nginx base image
FROM nginx:latest

# Copy a basic Nginx configuration
COPY nginx.conf /etc/nginx/nginx.conf

# Copy static files (if any) to the default Nginx directory
COPY static/ /usr/share/nginx/html/

# Expose port 80 for Nginx
EXPOSE 80

# Start Nginx (default CMD in the Nginx base image)
CMD ["nginx", "-g", "daemon off;"]
