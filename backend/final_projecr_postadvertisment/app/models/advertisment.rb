class Advertisment < ApplicationRecord
	belongs_to :user, required: false
	has_many :addresses, :dependent => :destroy 
	has_many :images, :dependent => :destroy

	validates :status, inclusion: { in: [ true, false ] }, acceptance: true
	validates :title_post, presence: true
	validates :price, presence: true
end
