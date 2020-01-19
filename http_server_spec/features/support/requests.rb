require "httparty"

module Requests

  def self.get(path)
    base_url = "#{PROTOCOL}://#{HOSTNAME}:#{PORT}"
    Response.new(HTTParty.get("#{base_url}#{path}", follow_redirects: false))
  end

  def self.head(path)
    base_url = "#{PROTOCOL}://#{HOSTNAME}:#{PORT}"
    Response.new(HTTParty.head("#{base_url}#{path}"))
  end

  def self.options(path)
    base_url = "#{PROTOCOL}://#{HOSTNAME}:#{PORT}"
    Response.new(HTTParty.options("#{base_url}#{path}"))
  end

  def self.post(path, body="")
    base_url = "#{PROTOCOL}://#{HOSTNAME}:#{PORT}"
    Response.new(HTTParty.post("#{base_url}#{path}", {body: body}))
  end

  def self.delete_request(path)
    base_url = "#{PROTOCOL}://#{HOSTNAME}:#{PORT}"
    Response.new(HTTParty.delete(base_url))
  end

  def self.bad_request(request_string)
    @socket = TCPSocket.open("#{HOSTNAME}", "#{PORT}")
    @socket.puts(request_string)
    read_from_socket
  end

  private

  def self.read_from_socket
     response = ""
     while line = @socket.gets
        response += line
    end
    response
  end

end
