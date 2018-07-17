class Advertisment < ApplicationRecord
	belongs_to :user, required: false
	has_many :addresses
	has_many :images
end
