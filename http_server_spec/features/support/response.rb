class Response

  def initialize(httpartyResponse)
    self.httpartyResponse = httpartyResponse
  end

  def location
    httpartyResponse.headers["location"]
  end

  def allowed_headers
    httpartyResponse.headers["allow"].split(/[ \t]*,[ \t]*/)
  end

  def body
    httpartyResponse.body.to_s
  end

  def status_code
    httpartyResponse.code
  end

  def self.get_status_code_from_raw_string_response(response)
    response.split(" ")[1].to_i
  end

  private

  attr_accessor :httpartyResponse


end
