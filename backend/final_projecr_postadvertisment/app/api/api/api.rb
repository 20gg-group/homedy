require 'grape-swagger'

module Api
  class Api < Grape::API
    format :json
    version 'v1'
    prefix :api

    rescue_from ActiveRecord::RecordNotFound do |e|
      error!(e, 500)
    end    
    rescue_from :all

    mount V1::AdvertismentApi
    mount V1::AddressApi 
    mount V1::ImageApi 
    add_swagger_documentation
  end
end