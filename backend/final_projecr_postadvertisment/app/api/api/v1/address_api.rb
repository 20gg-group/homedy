module Api::V1
  class AddressApi < Grape::API
    helpers do
      def address
        @address ||= Address.find(params[:id])
      end      
    end    
    resources :addresses do  

      # method GET id cua      
      namespace :advertisment do
        get ":id" do # get addresses/advertisment/:id
          present Address.find_by advertisment_id: params[:id] 
        end
      end
        get ":id" do # get addresses/:id
          present address
        end
        get do        # test thoi chac khong xai nhieu
          present Address.all
        end

      # method POST
      params do
        requires :city, type: String
        optional :district, type: String 
        optional :street, type: String
        optional :house_no, type: Integer
      end
      post do
        # post address--- find address repond advertisment of address
         #present Address.find(params)
          # if address ?
          #   present 
          # else
            
          # end

      end

      # method PUT 
      params do
        optional :city, type: String
        optional :district, type: String
        optional :house_no, type: Integer
        optional :street, type: String
      end
      put ":id" do
        address.update_attributes(declared(params))
        present address
      end
      #method DELETE
      
    end
    
  end
end