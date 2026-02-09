# Use the official Nginx base image
FROM nginx:latest

# Remove the default configuration
#RUN rm -f /etc/nginx/conf.d/default.conf

# Copy the custom Nginx configuration
COPY nginx.conf /etc/nginx/nginx.conf

# Copy static files to the default Nginx directory
COPY static/ /usr/share/nginx/html/tutorial/

# Generate a random 5-digit number and append it to the index.html file
RUN sh -c 'echo "<p>Random Number: $((RANDOM % 90000 + 10000))</p>" >> /usr/share/nginx/html/tutorial/index.html'

# Expose port 80 for Nginx
EXPOSE 80

# Start Nginx with the custom configuration
CMD ["nginx", "-c", "/etc/nginx/nginx.conf", "-g", "daemon off;"]
