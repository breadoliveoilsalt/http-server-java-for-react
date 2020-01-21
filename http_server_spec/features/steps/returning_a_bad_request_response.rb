require 'socket'

class Spinach::Features::ReturningABadRequestResponse < Spinach::FeatureSteps
  step 'I make a nonsensical request to the server' do
    @response = Requests.bad_request("jibberish-ladskfjh")
  end

  step 'I make a request with a misordered status line' do
    @response = Requests.bad_request("HTTP/1.1 GET /simple_get")
  end

  step 'my response should have a status code of 400' do
    status_code = Response.get_status_code_from_raw_string_response(@response)
    expect(status_code).to eq 400
  end

end
